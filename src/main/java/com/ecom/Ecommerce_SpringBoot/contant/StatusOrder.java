package com.ecom.Ecommerce_SpringBoot.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusOrder {

    IN_PROGRESS(1, "In Progress"),
    ORDER_RECEIVED(2, "Order Received"),
    PRODUCT_PACKED(3, "Product Packed"),
    OUT_FOR_DELIVERY(4, "Out for Delivery"),
    DELIVERED(5, "Delivered"),
    CANCEL(6, "Cancelled"),
    SUCCESS(7, "Success");

    private final Integer id;

    private final String name;
}
