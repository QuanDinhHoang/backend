package com.example.demo2.services.Impl;

import com.example.demo2.dtos.CategoryDTO;
import com.example.demo2.models.Category;
import com.example.demo2.repositories.CategoryReposity;
import com.example.demo2.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //Tu dong xay dung Constructor cho categoryRepository
public class CategoryService implements ICategoryService {
    private final CategoryReposity categoryReposity;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category.builder()
                        .name(categoryDTO.getName())
                        .build();
        return categoryReposity.save(newCategory);
    }

    @Override
    public Category getCategoryById(long categoryId) {
        return categoryReposity.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryReposity.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId); // su dung ham getCategoryById o tren
        existingCategory.setName(categoryDTO.getName());
        categoryReposity.save(existingCategory);
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryReposity.deleteById(id);
    }
}
