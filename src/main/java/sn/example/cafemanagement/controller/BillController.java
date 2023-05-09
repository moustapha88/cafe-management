package sn.example.cafemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.entities.Bill;
import sn.example.cafemanagement.rest.BillRest;
import sn.example.cafemanagement.service.BillService;
import sn.example.cafemanagement.utils.CafeUtils;

@RestController
public class BillController implements BillRest{

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateBill(Map<String, Object> requestMap) {
        try {
            billService.generateBill(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            billService.getBills();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Bill>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            billService.getPdf(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        
        try {
            billService.deleteBill(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
