package com.ecom.Ecommerce_SpringBoot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum StatusOrder {

    IN_PROGRESS(1, "In Progress"), ORDER_RECEIVED(2, "Order Received"),
    PRODUCT_PACKED(3, "Product Packed"), OUT_FOR_DELIVERY(4, "Out for Delivery"),
    DELIVERED(5, "Delivered");

    private int id;

    private String name;
}
