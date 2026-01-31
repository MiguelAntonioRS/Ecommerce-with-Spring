package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.repository.CartRepository;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import com.ecom.Ecommerce_SpringBoot.service.CartService;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public Cart cartSave(int productId, int userId) {
        UserDtls user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();

        if (product == null || user == null) {
            throw new RuntimeException("User or Product not found");
        }
        if (product.getStock() <= 0) {
            throw new RuntimeException("Out of stock");
        }
        Cart existingCart = cartRepository.findByProductIdAndUserId(productId, userId);

        if (existingCart == null) {

            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setQuantity(1);
            newCart.setTotalPrice(product.getDiscountPrice());
            product.setStock(product.getStock() - 1);
            productService.saveProduct(product);

            return cartRepository.save(newCart);
        } else {
            int newQuantity = existingCart.getQuantity() + 1;

            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Not enough stock");
            }

            existingCart.setQuantity(newQuantity);
            existingCart.setTotalPrice(newQuantity * product.getDiscountPrice());
            product.setStock(product.getStock() - 1);
            productService.saveProduct(product);

            return cartRepository.save(existingCart);
        }
    }

    @Override
    public List<Cart> getCartsByUser(int userId) {

        List<Cart> cartList = cartRepository.findByUserId(userId);
        double totalOrderPrice = 0.0;
        List<Cart> updateCarts = new ArrayList<>();

        for (Cart c:cartList) {
            double totalPrice = (c.getProduct().getDiscountPrice() * c.getQuantity());
            c.setTotalPrice(totalPrice);
            totalOrderPrice = totalOrderPrice + totalPrice;
            c.setTotalPriceOrders(totalOrderPrice);
            updateCarts.add(c);
        }
        return updateCarts;
    }

    @Override
    public int getCountCart(int userId) {

        int countByUserId = cartRepository.countByUserId(userId);

        return countByUserId;
    }

    @Override
    public void updateQuantity(String action, int cartId) {
        return null;
    }
}
