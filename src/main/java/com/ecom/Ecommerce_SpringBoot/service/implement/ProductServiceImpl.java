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

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Product saveImageProduct(Product product, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "product_img")
            );

            String imageUrl = uploadResult.get("secure_url").toString();
            product.setImage(imageUrl);
        }

        return productRepository.save(product);
    }

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

    @Override
    public Product updateProduct(Product product, MultipartFile file) {
        return productDAO.updateProduct(product, file);
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
