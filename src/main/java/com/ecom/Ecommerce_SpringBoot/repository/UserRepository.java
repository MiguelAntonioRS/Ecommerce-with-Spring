package com.ecom.Ecommerce_SpringBoot.repository;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    UserDtls findByEmail(String email);

    List<UserDtls> findByRole(String role);

    UserDtls findByResetToken(String token);
}
