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
public class AddressOrder {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String address;

    private String city;

    private String state;

    private String pincode;
}
