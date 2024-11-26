package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface CategoryDAO {

    public Category saveCategory(Category category);

    public List<Category> getAllCategory();
}
