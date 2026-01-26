package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartDAO {

    public Cart cartSave(int productId, int userId);

    public List<Cart> getCartsByUser(int userId);

    public int getCountCart(int userId);
}
