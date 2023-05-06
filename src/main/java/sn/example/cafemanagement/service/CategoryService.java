package sn.example.cafemanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import sn.example.cafemanagement.entities.Category;

public interface CategoryService {

    ResponseEntity<String> addNewCategorie(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategories(String filterValue);

    ResponseEntity<String> update(Map<String, String> requestMap);
    
}
