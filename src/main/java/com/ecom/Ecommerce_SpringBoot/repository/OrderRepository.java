package com.ecom.Ecommerce_SpringBoot.repository;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Integer> {

    List<ProductOrder> findByUserId(int userId);
}
