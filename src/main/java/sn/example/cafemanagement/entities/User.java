package sn.example.cafemanagement.entities;

import javax.persistence.*;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")

@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user")
public class User implements Serializable{

    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 100)
    private String username;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "contactNumber", length = 9)
    private String contactNumber;
    @Column(name = "password", length = 100)
    private String password;
    @Column(name = "status", length = 100)
    private String status;
    @Column(name = "role", length = 100)
    private String role;

}
