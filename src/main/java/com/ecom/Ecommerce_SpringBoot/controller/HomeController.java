package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Directorio de imágenes en Render
    private static final String UPLOAD_DIR = "/tmp/img";

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
    public String home(Model model) {
        model.addAttribute("isHome", true);
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
        // Crear directorio si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR, "profile_img");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String imageName = "default.jpg";
        if (!file.isEmpty()) {
            // Generar nombre único y seguro
            imageName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "_");
            Path filePath = uploadPath.resolve(imageName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        user.setProfileImage(imageName);
        UserDtls saveUser = userService.saveUser(user);

        if (!ObjectUtils.isEmpty(saveUser)) {
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
    public String processForgotPasswordPage(@RequestParam String email, HttpSession session, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        UserDtls userByEmail = userService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(userByEmail)) {
            session.setAttribute("errorMsg", "Invalid Email");
        } else {
            String resetToken = UUID.randomUUID().toString();
            userService.updateUserResetToken(email, resetToken);
            String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;
            Boolean sendMail = commonUtil.sendMail(url, email);
            if (sendMail) {
                session.setAttribute("succMsg", "Please check you mail: Password Reset");
            } else {
                session.setAttribute("errorMsg", "Something wrong on server");
            }
        }
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, HttpSession session, Model model) {
        UserDtls userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            model.addAttribute("msg", "Your link is invalid or expired");
            return "message";
        }
        model.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, @RequestParam String password, HttpSession session, Model model) {
        UserDtls userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            model.addAttribute("errorMsg", "Your link is invalid or expired");
            return "message";
        } else {
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            userService.updateUser(userByToken);
            model.addAttribute("msg", "Password change successfully");
            return "message";
        }
    }

    // Endpoint para servir imágenes de perfil
    @GetMapping("/images/profile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveProfileImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR, "profile_img", filename);
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header("Cache-Control", "max-age=31536000")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
}