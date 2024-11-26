package com.ecom.Ecommerce_SpringBoot.repository;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Boolean existsByName(String name);
}
