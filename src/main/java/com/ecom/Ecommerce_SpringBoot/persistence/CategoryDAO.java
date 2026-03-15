package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryDAO {

    Category saveCategory(Category category);

    List<Category> getAllCategory();

    Boolean existCategory(String name);

    Boolean deleteCategory(int id);

    Category getCategoryById(int id);

    List<Category> getAllActiveCategory();
}
