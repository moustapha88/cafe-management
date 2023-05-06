package sn.example.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.example.cafemanagement.entities.Product;
import sn.example.cafemanagement.wrapper.ProductWrapper;

public interface ProductDao extends JpaRepository<Product, Integer>{

    List<ProductWrapper> getAllProduct();
    
}
