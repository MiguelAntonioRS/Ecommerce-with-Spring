package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.UserDAO;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

    @Override
    public UserDtls saveUser(UserDtls user) {
        return null;
    }
}
