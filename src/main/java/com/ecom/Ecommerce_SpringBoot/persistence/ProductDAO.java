package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductDAO {

    public Product saveProduct(Product product);
}
