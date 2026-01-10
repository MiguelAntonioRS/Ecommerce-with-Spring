package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @ModelAttribute
    public void getUsersDetails(Principal principal, Model model) {

        if (principal != null) {

            String email = principal.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            model.addAttribute("user", userDtls);
        }
    }
}
