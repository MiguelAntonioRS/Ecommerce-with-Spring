package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserDtls user;

    @ManyToOne
    private Product product;

    private int quantity;

    @Transient
    private double totalPrice;

    @Transient
    private double totalAmountOrders;
}
