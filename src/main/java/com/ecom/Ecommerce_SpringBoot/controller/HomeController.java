package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.*;
import com.ecom.Ecommerce_SpringBoot.service.*;
import com.ecom.Ecommerce_SpringBoot.service.CloudinaryService;
import com.ecom.Ecommerce_SpringBoot.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final CommonUtil commonUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @ModelAttribute
    public void getUsersDetails(Principal principal, Model model) {

        if (principal != null) {
            String email = principal.getName();
            UserDtls user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
            model.addAttribute("countCart", cartService.getCountCart(user.getId()));
        }
        model.addAttribute("categorys", categoryService.getAllActiveCategory());
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("isHome", true);
        return "index";
    }

    @GetMapping("/guide")
    public String showUserGuide() {
        return "guide";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        model.addAttribute("categories", categoryService.getAllActiveCategory());
        model.addAttribute("products", productService.getAllActiveProducts(category));
        model.addAttribute("paramValue", category);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "view_product";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) {
        String profileUrl = "https://res.cloudinary.com/demo/image/upload/v1/default-profile.png";
        if (!file.isEmpty()) {
            try {
                profileUrl = cloudinaryService.uploadImage(file, "profiles");
            } catch (IOException e) {
                session.setAttribute("errorMsg", "Profile image upload failed");
                return "redirect:/register";
            }
        }
        user.setProfileImage(profileUrl);
        UserDtls saved = userService.saveUser(user);

        if (saved != null) {
            session.setAttribute("succMsg", "Registered successfully");
            return "redirect:/signin";
        } else {
            session.setAttribute("errorMsg", "Registration failed");
            return "redirect:/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPasswordPage(@RequestParam String email, HttpSession session, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        UserDtls user = userService.getUserByEmail(email);
        if (user == null) {
            session.setAttribute("errorMsg", "Invalid Email");
        } else {
            String token = java.util.UUID.randomUUID().toString();
            userService.updateUserResetToken(email, token);
            String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + token;
            if (commonUtil.sendMail(url, email)) {
                session.setAttribute("succMsg", "Check your email to reset password");
            } else {
                session.setAttribute("errorMsg", "Email sending failed");
            }
        }
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        UserDtls user = userService.getUserByToken(token);
        if (user == null) {
            model.addAttribute("msg", "Link expired or invalid");
            return "message";
        }
        model.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, @RequestParam String password, Model model) {
        UserDtls user = userService.getUserByToken(token);
        if (user == null) {
            model.addAttribute("errorMsg", "Invalid link");
            return "message";
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        userService.updateUser(user);
        model.addAttribute("msg", "Password changed successfully");
        return "message";
    }

    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {
        model.addAttribute("products", productService.searchProduct(search));
        return "product";
    }
}