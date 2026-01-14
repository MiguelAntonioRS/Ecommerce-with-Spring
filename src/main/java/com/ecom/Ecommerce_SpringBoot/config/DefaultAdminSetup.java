// src/main/java/com/ecom/Ecommerce_SpringBoot/config/DefaultAdminSetup.java

package com.ecom.Ecommerce_SpringBoot.config;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminSetup {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner init() {
        return args -> {
            String adminEmail = "admin@example.com";
            String adminPassword = "admin123";

            if (userRepository.findByEmail(adminEmail) == null) {
                UserDtls admin = new UserDtls();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ROLE_ADMIN");
                admin.setAccountNonBlocked(true);
                admin.setMobileNumber("1234567890");
                admin.setAddress("Admin Address");
                admin.setCity("Admin City");
                admin.setState("Admin State");
                admin.setPinCode("12345");
                admin.setProfileImage("default.jpg");

                userRepository.save(admin);
                System.out.println("✅ Admin creado: " + adminEmail);
            } else {
                System.out.println("ℹ️ Admin ya existe.");
            }
        };
    }
}