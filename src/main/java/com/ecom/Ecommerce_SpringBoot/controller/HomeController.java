package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUsersDetails(Principal principal, Model model) {

        if (principal != null) {

            String email = principal.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            model.addAttribute("user", userDtls);
        }

        List<Category> categories = categoryService.getAllActiveCategory();
        model.addAttribute("categorys", categories);
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String index() {
        return "index";
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

        List<Category> categories = categoryService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts(category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("paramValue", category);

        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model model) {

        Product productById = productService.getProductById(id);
        model.addAttribute("product", productById);

        return "view_product";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {

        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        user.setProfileImage(imageName);
        UserDtls saveUser = userService.saveUser(user);

        if (!ObjectUtils.isEmpty(saveUser)) {

            if (!file.isEmpty()) {

                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("succMsg", "Register successfully");
            return "redirect:/signin";
        } else {
            session.setAttribute("errorMsg", "Not Saved ! internal server error");
            return "redirect:/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {

        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPasswordPage(@RequestParam String email, HttpSession session) {

        UserDtls userByEmail = userService.getUserByEmail(email);

        if (ObjectUtils.isEmpty(userByEmail)){
            session.setAttribute("msg", "Invalid Email");
        } else {
            Boolean sendMail = CommonUtil.sendMail();

            if (sendMail) {
                session.setAttribute("msg", "Please check you mail: Password Reset");
            }
        }

        return "forgot_password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage() {

        return "reset_password";
    }
}
