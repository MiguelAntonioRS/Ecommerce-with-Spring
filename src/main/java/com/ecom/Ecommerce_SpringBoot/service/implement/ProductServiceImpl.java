package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.persistence.ProductDAO;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public Product saveProduct(Product product) {
        return productDAO.saveProduct(product);
    }
}
