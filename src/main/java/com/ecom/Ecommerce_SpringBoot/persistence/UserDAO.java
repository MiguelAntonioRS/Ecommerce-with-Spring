package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDAO {

    public UserDtls saveUser(UserDtls user);

    public UserDtls getUserByEmail(String email);

    public List<UserDtls> getAllUsers(String role);

    public Boolean updateAccountStatus(Integer id, String status);
}
