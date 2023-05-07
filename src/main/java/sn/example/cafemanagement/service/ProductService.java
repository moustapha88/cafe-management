package sn.example.cafemanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import sn.example.cafemanagement.wrapper.ProductWrapper;

public interface ProductService {

    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> get();

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> delete(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id);

    ResponseEntity<ProductWrapper> getProductById(Integer id);
    
}
