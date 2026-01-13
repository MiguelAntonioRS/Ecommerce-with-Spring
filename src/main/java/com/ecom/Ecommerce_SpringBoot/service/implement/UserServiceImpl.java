package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.UserDAO;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDtls saveUser(UserDtls user) {
        return userDAO.saveUser(user);
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public List<UserDtls> getAllUsers() {
        return userDAO.;
    }
}
