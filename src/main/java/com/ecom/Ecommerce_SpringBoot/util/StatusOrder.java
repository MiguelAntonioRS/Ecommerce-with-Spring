package com.ecom.Ecommerce_SpringBoot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOrder {

    IN_PROGRESS(1, "In Progress"), ORDER_RECEIVED(2, "Order Received"),
    PRODUCT_PACKED(3, "Product Packed"), OUT_FOR_DELIVERY(4, "Out for Delivery"),
    DELIVERED(5, "Delivered"),CANCEL(6, "Cancelled");

    private final int id;

    private final String name;
}
