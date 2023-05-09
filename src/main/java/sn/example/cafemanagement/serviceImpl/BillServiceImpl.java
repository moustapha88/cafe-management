package sn.example.cafemanagement.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;
import sn.example.cafemanagement.JWT.JwtFilter;
import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.dao.BillDao;
import sn.example.cafemanagement.entities.Bill;
import sn.example.cafemanagement.service.BillService;
import sn.example.cafemanagement.utils.CafeUtils;

@Slf4j
@Service
public class BillServiceImpl implements BillService{

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateBill(Map<String, Object> requestMap) {
        try {
            String filename;
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")){
                    filename = (String) requestMap.get("uuid");
                }else{
                    filename = CafeUtils.getUUID();
                    requestMap.put("uuid", filename);
                    insertBill(requestMap);
                }
                generateDocument(filename,requestMap);

                return CafeUtils.getResponseEntity("{uuid : " +filename+ " }", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void generateDocument(String filename, Map<String, Object> requestMap) throws FileNotFoundException, DocumentException, JSONException{
        String data = "Name : " + requestMap.get("name") + "\n" + "Contact Number : " + requestMap.get("contactNumber") +
                            "\n" + "Email : " +requestMap.get("email") + "\n" + "Payment Method : " + requestMap.get("paymentMethod");                
        
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION+filename+".pdf")); 

        document.open();
        setRectangleInPdf(document);

        Paragraph header = new Paragraph("Cafe Management System", getFont("Header"));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        Paragraph paragraph = new Paragraph(data +"\n \n", getFont("Data"));
        document.add(paragraph);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        addTableHeader(table);

        JSONArray jsonArray = CafeUtils.getAJsonArrayFromString((String) requestMap.get("productDetails"));

        for(int i = 0; i < jsonArray.length(); i++){
            addRows(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
        }

        document.add(table);

        Paragraph footer = new Paragraph("total : "+requestMap.get("totalAmount")+"\n"
                        +"Thank you for visiting. Please visit again!! ", getFont("Data"));
        document.add(footer);

        document.close();
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");

        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));

    }

    /**
     * @param table
     */
    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");

        Stream.of("Name","Category","Quantity","Price","Total")
        .forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });

    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch(type) {
            case "Header" :
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);

        document.add(rect);
    }

    private void insertBill(Map<String, Object> requestMap){
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setProductDetail((String) requestMap.get("productDetails"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setCreatedBy(jwtFilter.getCurrentUser());

            billDao.save(bill);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
            && requestMap.containsKey("email") && requestMap.containsKey("paymentMethod")
            && requestMap.containsKey("productDetails") && requestMap.containsKey("totalAmount");
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if(jwtFilter.isAdmin()){
            list = billDao.getAllBills();
        }else{
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }

        return new ResponseEntity<List<Bill>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        
        log.info("Inside getPdf : requestMap {}", requestMap);
        try {
            byte[] byteArray = new byte[0];
            if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap)){
                return new ResponseEntity<>(byteArray ,HttpStatus.OK);
            }
            String filePath = CafeConstants.STORE_LOCATION+(String) requestMap.get("uuid") + "pdf";
            if(CafeUtils.isFileExist(filePath)){
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray ,HttpStatus.OK);
            }else{
                requestMap.put("isGenerate", false);
                generateBill(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray ,HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private byte[] getByteArray(String filePath) throws IOException {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        
        try {
            Optional<Bill> optional = billDao.findById(id);
            if(!optional.isEmpty()){
                billDao.deleteById(id);
                return CafeUtils.getResponseEntity("Bill Deleted Successfully", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Bill Id doesn't exist", HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }   
}
