package sn.example.cafemanagement.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sn.example.cafemanagement.dao.BillDao;
import sn.example.cafemanagement.dao.CategoryDao;
import sn.example.cafemanagement.dao.ProductDao;
import sn.example.cafemanagement.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> get() {
        
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("category", categoryDao.count());
        requestMap.put("product", productDao.count());
        requestMap.put("bill", billDao.count());
        return new ResponseEntity<>(requestMap, HttpStatus.OK);       
    }
    
}
