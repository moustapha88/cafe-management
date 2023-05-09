package sn.example.cafemanagement.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc")

@NamedQuery(name = "Bill.getBillByUserName", query = "select b from Bill b where b.createdBy=:username order by b.id desc")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "bill")
public class Bill implements Serializable{
    
    private static final long serialVersionUID = 12345l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "paymentmethod")
    private String paymentMethod;
    
    @Column(name = "totalAmount")
    private Integer total;

    @Column(name = "productDetails", columnDefinition = "json")
    private String productDetail;

    @Column(name = "createdby")
    private String createdBy;
}
