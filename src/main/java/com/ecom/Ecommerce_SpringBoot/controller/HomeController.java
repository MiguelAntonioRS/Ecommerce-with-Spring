package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.repository.ProductRepository;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model) {

        List<Category> categories = categoryService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "product";
    }

    @GetMapping("/product")
    public String product(Model model) {
        return "view_product";
    }
}
