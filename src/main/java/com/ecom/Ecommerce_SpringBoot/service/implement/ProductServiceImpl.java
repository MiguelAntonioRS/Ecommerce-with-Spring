package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.persistence.ProductDAO;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productDAO.saveProduct(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        return productDAO.deleteProduct(id);
    }

    @Override
    public Product getProductById(Integer id) {
        return productDAO.getProductById(id);
    }

    private void calculateDiscountPrice(Product product) {
        if (product.getPrice() != null && product.getDiscount() != null) {
            double discount = product.getDiscount() / 100.0;
            product.setDiscountPrice(product.getPrice() * (1 - discount));
        } else {
            product.setDiscountPrice(product.getPrice());
        }
    }

    @Override
    public Product updateProduct(Product product) {
        calculateDiscountPrice(product);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        return productDAO.getAllActiveProducts(category);
    }

    @Override
    public List<Product> searchProduct(String search) {
        return productDAO.searchProduct(search);
    }
}
