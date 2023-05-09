package sn.example.cafemanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import sn.example.cafemanagement.rest.DashboardRest;
import sn.example.cafemanagement.service.DashboardService;

@RestController
public class DashboardController implements DashboardRest{

    @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> get() {
        try {
            dashboardService.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
