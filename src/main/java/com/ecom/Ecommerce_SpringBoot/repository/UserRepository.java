package com.ecom.Ecommerce_SpringBoot.repository;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
}
