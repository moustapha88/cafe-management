package sn.example.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import sn.example.cafemanagement.entities.Bill;

public interface BillDao extends JpaRepository<Bill, Integer>{

    List<Bill> getAllBills();

    List<Bill> getBillByUserName(@Param("username") String username);
    
}
