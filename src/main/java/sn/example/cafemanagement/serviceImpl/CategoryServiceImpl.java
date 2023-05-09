package sn.example.cafemanagement.serviceImpl;

import sn.example.cafemanagement.JWT.JwtFilter;
import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.dao.CategoryDao;
import sn.example.cafemanagement.entities.Category;
import sn.example.cafemanagement.service.CategoryService;
import sn.example.cafemanagement.utils.CafeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategorie(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(valideCategoryMap(requestMap,false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Category added successfully", HttpStatus.OK);
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
        List<Category> list = new ArrayList<Category>();
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("inside if");
                list = categoryDao.getAllCategory();
                return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
            }
            list = categoryDao.findAll();
            return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(valideCategoryMap(requestMap, true)){
                    Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity("Category updated succesfully", HttpStatus.OK);
                    }else{
                        return CafeUtils.getResponseEntity("Category id doesn't exist", HttpStatus.BAD_REQUEST);
                    }
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    private boolean valideCategoryMap(Map<String, String> requestMap, boolean valideId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && valideId){
                return true;
            }else if (!valideId){
                return true;
            }
        }
        return false;
    }
    
}
