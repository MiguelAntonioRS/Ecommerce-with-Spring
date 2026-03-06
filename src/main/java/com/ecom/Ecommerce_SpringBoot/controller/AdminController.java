package com.ecom.Ecommerce_SpringBoot.controller;

import com.ecom.Ecommerce_SpringBoot.contant.StatusOrder;
import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.entities.Product;
import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import com.ecom.Ecommerce_SpringBoot.service.CloudinaryService;
import com.ecom.Ecommerce_SpringBoot.service.OrderService;
import com.ecom.Ecommerce_SpringBoot.service.ProductService;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final CommonUtil commonUtil;
    private final CloudinaryService cloudinaryService;

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
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("product", new Product());
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categorys", categoryService.getAllCategory());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) {
        String imageUrl = "https://res.cloudinary.com/demo/image/upload/v1/default.jpg"; // fallback público
        if (!file.isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadImage(file, "categories");
            } catch (IOException e) {
                session.setAttribute("errorMsg", "Image upload failed");
                return "redirect:/admin/category";
            }
        }
        category.setImageName(imageUrl);

        Boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errorMsg", "Category Name already exists");
        } else {
            Category saved = categoryService.saveCategory(category);
            if (saved != null) {
                session.setAttribute("succMsg", "Saved successfully");
            } else {
                session.setAttribute("errorMsg", "Internal server error");
            }
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            session.setAttribute("succMsg", "Category deleted");
        } else {
            session.setAttribute("errorMsg", "Server error");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) {
        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageUrl = oldCategory.getImageName();

        if (!file.isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadImage(file, "categories");
            } catch (IOException e) {
                session.setAttribute("errorMsg", "Image update failed");
                return "redirect:/admin/loadEditCategory/" + category.getId();
            }
        }

        oldCategory.setName(category.getName());
        oldCategory.setIsActive(category.getIsActive());
        oldCategory.setImageName(imageUrl);

        Category updated = categoryService.saveCategory(oldCategory);
        if (updated != null) {
            session.setAttribute("succMsg", "Category updated");
        } else {
            session.setAttribute("errorMsg", "Server error");
        }
        return "redirect:/admin/loadEditCategory/" + category.getId();
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult result,
                              @RequestParam("file") MultipartFile image, HttpSession session, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategory());
            return "admin/add_product";
        }

        String imageUrl = "https://res.cloudinary.com/demo/image/upload/v1/default.jpg";
        if (!image.isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadImage(image, "products");
            } catch (IOException e) {
                session.setAttribute("errorMsg", "Image upload failed");
                return "redirect:/admin/loadAddProduct";
            }
        }

        product.setImage(imageUrl);
        product.setDiscountPrice(product.getPrice()); // ajusta si usas descuento

        Product saved = productService.saveProduct(product);
        if (saved != null) {
            session.setAttribute("succMsg", "Product saved");
            return "redirect:/admin/products";
        } else {
            session.setAttribute("errorMsg", "Server error");
            return "redirect:/admin/loadAddProduct";
        }
    }

    @GetMapping("/products")
    public String loadViewProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        Boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            session.setAttribute("succMsg", "Product deleted");
        } else {
            session.setAttribute("errorMsg", "Server error");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/edit_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
                                HttpSession session) {
        String currentImageUrl = product.getImage();
        if (!image.isEmpty()) {
            try {
                currentImageUrl = cloudinaryService.uploadImage(image, "products");
            } catch (IOException e) {
                session.setAttribute("errorMsg", "Image update failed");
                return "redirect:/admin/editProduct/" + product.getId();
            }
        }

        product.setImage(currentImageUrl);
        Product updated = productService.updateProduct(product);

        // Después de asignar la imagen...
        if (product.getDiscount() == null) {
            product.setDiscount(0);
        }
        if (product.getPrice() != null) {
            double discountPercent = product.getDiscount() / 100.0;
            product.setDiscountPrice(product.getPrice() * (1 - discountPercent));
        }

        if (updated != null) {
            session.setAttribute("succMsg", "Product updated");
        } else {
            session.setAttribute("errorMsg", "Server error");
        }
        return "redirect:/admin/editProduct/" + product.getId();
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers("ROLE_USER"));
        return "admin/users";
    }

    @GetMapping("/updateStatus")
    public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
        Boolean updated = userService.updateAccountStatus(id, status);
        if (updated) {
            session.setAttribute("succMsg", "Account status updated");
        } else {
            session.setAttribute("errorMsg", "Server error");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersByUser());
        return "admin/orders";
    }

    @PostMapping("/status-order-update")
    public String updateStatusOrder(@RequestParam int id, @RequestParam int status, HttpSession session) {
        StatusOrder[] values = StatusOrder.values();
        String oStatus = null;
        for (StatusOrder s : values) {
            if (s.getId().equals(status)) {
                oStatus = s.getName();
                break;
            }
        }

        ProductOrder order = orderService.orderStatusUpdate(id, oStatus);
        try {
            commonUtil.sendOrderMail(order, oStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (order != null) {
            session.setAttribute("succMsg", "Status updated");
        } else {
            session.setAttribute("errorMsg", "Update failed");
        }
        return "redirect:/admin/orders";
    }
}