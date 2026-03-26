package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Boolean deleteProduct(Integer id);

    Product getProductById(Integer id);

    Product updateProduct(Product product);

    List<Product> getAllActiveProducts(String category);

    List<Product> searchProduct(String search);

    //Product saveImageProduct(Product product, MultipartFile file) throws IOException;
}
