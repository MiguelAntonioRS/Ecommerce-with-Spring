package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.persistence.CartDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDAOImpl implements CartDAO {

    @Override
    public Cart cartSave(int productId, int userId) {
        return null;
    }

    @Override
    public List<Cart> getCartsByUser(int userId) {
        return null;
    }
}
