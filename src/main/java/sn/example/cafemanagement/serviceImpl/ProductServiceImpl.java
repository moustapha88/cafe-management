package sn.example.cafemanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sn.example.cafemanagement.JWT.JwtFilter;
import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.dao.ProductDao;
import sn.example.cafemanagement.entities.Category;
import sn.example.cafemanagement.entities.Product;
import sn.example.cafemanagement.service.ProductService;
import sn.example.cafemanagement.utils.CafeUtils;
import sn.example.cafemanagement.wrapper.ProductWrapper;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Product added successfully>", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> get() {
        try {
            return new ResponseEntity<List<ProductWrapper>>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        productDao.save(getProductFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);
                    }
                    return CafeUtils.getResponseEntity("Product id doesn't exist ", HttpStatus.OK);                
                }
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @Override
    public ResponseEntity<String> delete(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<Product> optional = productDao.findById(id);
                if(!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CafeUtils.getResponseEntity("Product deleted successfully.", HttpStatus.OK);                    
                }
                return CafeUtils.getResponseEntity("Product id doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt("id"));
                        return CafeUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);
                    }
                    return CafeUtils.getResponseEntity("Product id doesn't exist ", HttpStatus.OK);                
                }
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @Override
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id) {
        try {
            return new ResponseEntity<List<ProductWrapper>>(productDao.getProductByCategoryId(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean valideId){
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && valideId){
                return true;
            }else{
                return true;
            }
        }
        return false;
    }


}
