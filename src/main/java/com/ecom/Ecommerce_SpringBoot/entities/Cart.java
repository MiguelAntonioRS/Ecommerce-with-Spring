package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.Entity;
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

    private int id;

    private UserDtls user;

    private Product product;

    private int quantity;
}
