package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.Entity;
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

    private int id;

    private String orderId;

    private Date orderDate;

    private Product product;

    private double price;

    private int quantity;

    private UserDtls user;

    private String status;

    private String paymentType;
}
