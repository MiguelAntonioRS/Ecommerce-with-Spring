package com.ecom.Ecommerce_SpringBoot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String password;

    private String profileImage;

    private String role;

    private Boolean isEnabled;

    @Column
    private Boolean accountNonBlocked = true;

    @Column
    private Integer failedAttempt = 0;

    private Date lockTime;
}
