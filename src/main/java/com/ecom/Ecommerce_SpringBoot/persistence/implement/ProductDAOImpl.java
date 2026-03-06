package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.persistence.ProductDAO;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {

        Product product = productRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(product)) {

            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {

        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Product dbProduct = getProductById(product.getId());
        if (dbProduct == null) {
            return null;
        }

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setIsActive(product.getIsActive());
        dbProduct.setDiscount(product.getDiscount());

        double discount = product.getPrice() * (product.getDiscount() / 100.0);
        double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);

        if (!image.isEmpty()) {
            try {
                String UPLOAD_DIR = "/tmp/img";
                Path uploadPath = Paths.get(UPLOAD_DIR, "product_img");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename().replace(" ", "_");
                Path filePath = uploadPath.resolve(imageName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                dbProduct.setImage(imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return productRepository.save(dbProduct);
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {

        List<Product> products = null;
        if (ObjectUtils.isEmpty(category)) {
            products = productRepository.findByIsActiveTrue();
        } else {
            products = productRepository.findByCategory(category);
        }

        return products;
    }

    @Override
    public List<Product> searchProduct(String search) {
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search);
    }
}
