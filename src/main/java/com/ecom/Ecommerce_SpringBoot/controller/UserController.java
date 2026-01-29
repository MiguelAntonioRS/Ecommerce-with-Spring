package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.service.CartService;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    @ModelAttribute
    public void getUsersDetails(Principal principal, Model model) {

        if (principal != null) {

            String email = principal.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            model.addAttribute("user", userDtls);
            Integer countCart = cartService.getCountCart(userDtls.getId());
            model.addAttribute("countCart", countCart);
        }

        List<Category> categories = categoryService.getAllActiveCategory();
        model.addAttribute("categorys", categories);
    }

    @GetMapping("/addCart")
    public String addToCart(@RequestParam int productId, @RequestParam int userId, HttpSession session) {

        Cart saveCart = cartService.cartSave(productId, userId);

        if (ObjectUtils.isEmpty(saveCart)) {
            session.setAttribute("errorMsg", "Error non-added product");
        } else {
            session.setAttribute("succMsg", "Product added to cart successfully");
        }

        return "redirect:/product/" + productId;
    }

    @GetMapping("/cart")
    public String cartPage(Principal principal, Model model) {

        UserDtls userDtls = getLoggedInUserDetails(principal);
        List<Cart> carts = cartService.getCartsByUser(userDtls.getId());
        model.addAttribute("carts", carts);

        return "/user/cart";
    }

    private UserDtls getLoggedInUserDetails(Principal principal) {

        String email = principal.getName();
        UserDtls userDtls = userService.getUserByEmail(email);

        return userDtls;
    }
}
