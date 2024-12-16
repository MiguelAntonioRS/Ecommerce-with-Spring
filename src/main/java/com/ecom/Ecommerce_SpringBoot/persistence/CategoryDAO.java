package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface CategoryDAO {

    public Category saveCategory(Category category);

    public List<Category> getAllCategory();

    public Boolean existCategory(String name);

    public Boolean deleteCategory(int id);

    public Category getCategoryById(int id);

    public List<Category> getAllActiveCategory();
}
