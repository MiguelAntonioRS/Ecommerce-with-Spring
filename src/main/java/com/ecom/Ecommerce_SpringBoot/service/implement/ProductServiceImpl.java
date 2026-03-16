package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.persistence.ProductDAO;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ProductRepository productRepository;

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
