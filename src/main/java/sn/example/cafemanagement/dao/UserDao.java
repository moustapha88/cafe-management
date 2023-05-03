package sn.example.cafemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import sn.example.cafemanagement.entities.User;

public interface UserDao extends JpaRepository<User, Integer>{
    User findByEmailId(@Param("email") String email);
}
