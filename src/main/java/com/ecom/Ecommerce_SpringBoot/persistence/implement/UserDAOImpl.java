package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.UserDAO;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDtls saveUser(UserDtls user) {

        user.setRole("ROLE_USER");
        user.setIsEnabled(true);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        UserDtls saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDtls> getAllUsers(String role) {
        return userRepository.findByRole(role);
    }
}
