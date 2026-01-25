package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.repository.CartRepository;
import com.ecom.Ecommerce_SpringBoot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart cartSave(int productId, int userId) {
        return null;
    }

    @Override
    public List<Cart> getCartsByUser(int userId) {
        return null;
    }
}
