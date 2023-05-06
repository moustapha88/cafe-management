package sn.example.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.example.cafemanagement.entities.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    
    List<Category> getAllCategory();
}
