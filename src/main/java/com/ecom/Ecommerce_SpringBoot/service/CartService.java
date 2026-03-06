package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    Cart cartSave(int productId, int userId);

    List<Cart> getCartsByUser(int userId);

    int getCountCart(int userId);

    void updateQuantity(String action, int cartId);
}
