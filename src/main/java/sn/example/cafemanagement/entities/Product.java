package sn.example.cafemanagement.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "Product.getAllProduct", query = "select new sn.example.cafemanagement.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")

@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")

@NamedQuery(name = "Product.getProductByCategoryId", query = "select new sn.example.cafemanagement.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and status='true'")

@NamedQuery(name = "Product.getProductById", query = "select new sn.example.cafemanagement.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) from Product p where p.id=:id")



@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product implements Serializable{
    
    private static final long serialVersionUID = 123456l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;
}
