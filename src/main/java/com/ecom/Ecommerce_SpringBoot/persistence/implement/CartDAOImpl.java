package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.CartDAO;
import com.ecom.Ecommerce_SpringBoot.repository.CartRepository;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class CartDAOImpl implements CartDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart cartSave(int productId, int userId) {

        UserDtls userDtls = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        Cart statusCart = cartRepository.findByProductIdAndUserId(productId, userId);
        Cart cart = null;

        if (ObjectUtils.isEmpty(statusCart)) {
            cart = new Cart();
            cart.setProduct(product);
            cart.setUser(userDtls);
            cart.setQuantity(1);
            cart.setTotalPrice(1 * product.getDiscountPrice());
        } else {
            cart = statusCart;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice());
        }
        Cart saveCart = cartRepository.save(cart);

        return saveCart;
    }

    @Override
    public List<Cart> getCartsByUser(int userId) {
        return null;
    }

    @Override
    public int getCountCart(int userId) {

        int countByUserId = cartRepository.countByUserId(userId);

        return countByUserId;
    }

    @Override
    public Boolean updateQuantity(String action, int cartId) {

        Cart cart = cartRepository.findById(cartId).get();
        int update;

        if (action.equalsIgnoreCase("de")) {

            update = cart.getQuantity() - 1;

            if (update<=0) {

                cartRepository.deleteById(cartId);
                return true;
            }
        } else {
            update = cart.getQuantity() + 1;
        }
        cart.setQuantity(update);
        cartRepository.save(cart);

        return true;
    }
}
