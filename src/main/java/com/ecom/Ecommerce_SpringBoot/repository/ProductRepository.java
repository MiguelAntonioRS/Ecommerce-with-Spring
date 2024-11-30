package com.ecom.Ecommerce_SpringBoot.repository;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
