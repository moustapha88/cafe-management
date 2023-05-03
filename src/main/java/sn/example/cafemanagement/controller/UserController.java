package sn.example.cafemanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import sn.example.cafemanagement.constents.CafeConstants;
import sn.example.cafemanagement.rest.UserRest;
import sn.example.cafemanagement.service.UserService;
import sn.example.cafemanagement.utils.CafeUtils;

@RestController
public class UserController implements UserRest{

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
}