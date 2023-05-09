package sn.example.cafemanagement.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/dashboard")
public interface DashboardRest {

    @GetMapping(path = "/get")
    ResponseEntity<Map<String, Object>> get();
}
