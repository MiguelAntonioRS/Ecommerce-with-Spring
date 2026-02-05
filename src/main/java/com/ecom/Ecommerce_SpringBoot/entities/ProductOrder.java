package com.ecom.Ecommerce_SpringBoot.entities;

import java.util.Date;

public class ProductOrder {

    private int id;

    private String orderId;

    private Date orderDate;

    private Product product;

    private double price;

    private int quantity;

    private UserDtls user;

    private String status;
}
