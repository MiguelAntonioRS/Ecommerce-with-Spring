package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RequestOrder {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String paymentType;
}
