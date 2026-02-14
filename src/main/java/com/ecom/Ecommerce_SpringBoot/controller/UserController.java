package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.*;
import com.ecom.Ecommerce_SpringBoot.service.CartService;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.OrderService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.StatusOrder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private OrderService orderService;

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
        List<Cart> cartList = cartService.getCartsByUser(userDtls.getId());
        model.addAttribute("carts", cartList);

        if (cartList.size() > 0) {
            Double totalOrderPrice = cartList.get(cartList.size() - 1).getTotalPriceOrders();
            model.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "user/cart";
    }

    @GetMapping("/cartUpdate")
    public String updateQuantityCart(@RequestParam String action, @RequestParam("cid") int cartId) {

        cartService.updateQuantity(action, cartId);
        return "redirect:/user/cart";
    }

    @GetMapping("/orders")
    public String pageOrder(Principal principal, Model model) {

        UserDtls user = getLoggedInUserDetails(principal);
        List<Cart> carts = cartService.getCartsByUser(user.getId());
        model.addAttribute("carts", carts);

        if (carts.size() > 0) {
            Double orderPrice = carts.get(carts.size() -1).getTotalPriceOrders();
            Double totalOrderPrice = carts.get(carts.size() -1).getTotalPriceOrders() + 5 + 10;
            model.addAttribute("orderPrice", orderPrice);
            model.addAttribute("totalOrderPrice", totalOrderPrice);
        }

        return "user/order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute RequestOrder requestOrder, Principal principal) {

        UserDtls user = getLoggedInUserDetails(principal);
        orderService.saveOrder(user.getId(), requestOrder);

        return "redirect:/user/order-success";
    }

    @GetMapping("/order-success")
    public String loadSuccess() {
        return "user/order-success";
    }

    @GetMapping("/status-update")
    public String updateStatusOrder(@RequestParam int id, @RequestParam int status) {

        StatusOrder[] values = StatusOrder.values();
        System.out.println("Values: " + values);
        return "redirect:/user/orders";
    }

    @GetMapping("/user-orders")
    public String myOrder(Model model, Principal principal) {

        UserDtls logUser = getLoggedInUserDetails(principal);
        List<ProductOrder> orders = orderService.getOrdersByUser(logUser.getId());
        model.addAttribute("orders", orders);

        return "user/my-orders";
    }

    private UserDtls getLoggedInUserDetails(Principal principal) {

        String email = principal.getName();
        UserDtls userDtls = userService.getUserByEmail(email);

        return userDtls;
    }
}
