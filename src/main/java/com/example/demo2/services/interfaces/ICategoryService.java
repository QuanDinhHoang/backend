package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.CategoryDTO;
import com.example.demo2.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICategoryService {
    //May cai interface minh tu nghi ra dc
    Category createCategory(CategoryDTO category);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long id);

}
