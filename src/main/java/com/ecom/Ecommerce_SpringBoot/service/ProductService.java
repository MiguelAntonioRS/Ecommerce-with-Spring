package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Boolean deleteProduct(Integer id);

    public Product getProductById(Integer id);
}
