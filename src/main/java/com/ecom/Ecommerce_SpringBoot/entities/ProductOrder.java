package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String orderId;

    private Date orderDate;

    @ManyToOne
    private Product product;

    private double price;

    private int quantity;

    @ManyToOne
    private UserDtls user;

    private String status;

    private String paymentType;
}
