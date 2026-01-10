package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO {

    public UserDtls saveUser(UserDtls user);

    public UserDtls getUserByEmail(String email);
}
