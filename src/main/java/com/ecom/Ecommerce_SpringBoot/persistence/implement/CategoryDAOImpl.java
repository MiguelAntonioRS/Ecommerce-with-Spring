package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.Category;
import com.ecom.Ecommerce_SpringBoot.persistence.CategoryDAO;
import com.ecom.Ecommerce_SpringBoot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(category)) {
            categoryRepository.delete(category);
            return true;
        }

        return false;
    }
}
