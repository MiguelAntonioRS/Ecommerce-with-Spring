package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CartService {

    public Cart cartSave(int productId, int userId);

    public List<Cart> getCartsByUser(int userId);
}
