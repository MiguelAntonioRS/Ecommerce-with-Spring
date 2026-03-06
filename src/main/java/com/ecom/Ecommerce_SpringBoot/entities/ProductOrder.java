package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private LocalDate orderDate;

    @ManyToOne
    private Product product;

    private double price;

    private int quantity;

    @ManyToOne
    private UserDtls user;

    private String status;

    private String paymentType;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressOrder addressOrder;
}
