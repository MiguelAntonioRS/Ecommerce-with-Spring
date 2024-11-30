package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    public Product saveProduct(Product product);
}
