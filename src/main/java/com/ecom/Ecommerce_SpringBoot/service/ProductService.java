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

    Product updateProduct(Product product);

    public List<Product> getAllActiveProducts(String category);

    public List<Product> searchProduct(String search);

    //Product saveImageProduct(Product product, MultipartFile file) throws IOException;
}
