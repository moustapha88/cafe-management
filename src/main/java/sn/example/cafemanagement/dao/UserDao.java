package sn.example.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sn.example.cafemanagement.entities.User;
import sn.example.cafemanagement.wrapper.UserWrapper;

public interface UserDao extends JpaRepository<User, Integer>{
    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    List<String> getAllAdmin();

    User findByUsername(@Param("email") String username);
}
