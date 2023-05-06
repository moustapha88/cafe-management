package sn.example.cafemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.entities.Category;
import sn.example.cafemanagement.rest.CategoryRest;
import sn.example.cafemanagement.service.CategoryService;
import sn.example.cafemanagement.utils.CafeUtils;

@RestController
public class CategoryController implements CategoryRest{

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategorie(Map<String, String> requestMap) {
        try {
            categoryService.addNewCategorie(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
        try {
            categoryService.getAllCategories(filterValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            categoryService.update(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
