package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.persistence.CategoryDAO;
import com.ecom.Ecommerce_SpringBoot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Category saveCategory(Category category) {
        return categoryDAO.saveCategory(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDAO.getAllCategory();
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryDAO.existCategory(name);
    }

    @Override
    public Boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }
}
