package com.ecom.Ecommerce_SpringBoot.config;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultAdminSetup {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ApplicationRunner createDefaultAdmin() {
        return args -> {
            String adminEmail = System.getenv("ADMIN_EMAIL");
            String adminPassword = System.getenv("ADMIN_PASSWORD");

            // Valores por defecto si no hay variables de entorno
            if (adminEmail == null || adminEmail.isEmpty()) {
                adminEmail = "admin@example.com";
            }
            if (adminPassword == null || adminPassword.isEmpty()) {
                adminPassword = "admin123";
            }

            // Solo crea si no existe
            if (userRepository.findByEmail(adminEmail) == null) {
                UserDtls admin = new UserDtls();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ROLE_ADMIN");
                admin.setMobileNumber("0000000000");
                admin.setAddress("Admin Address");
                admin.setCity("Admin City");
                admin.setState("Admin State");
                admin.setPinCode("000000");
                admin.setProfileImage("default.jpg");

                userRepository.save(admin);
                System.out.println("✅ Admin creado: " + adminEmail);
            } else {
                System.out.println("ℹ️ Admin ya existe.");
            }
        };
    }
}