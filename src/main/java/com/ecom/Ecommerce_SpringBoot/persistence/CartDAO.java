package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartDAO {

    Cart cartSave(int productId, int userId);

    List<Cart> getCartsByUser(int userId);

    int getCountCart(int userId);

    void updateQuantity(String action, int cartId);
}
