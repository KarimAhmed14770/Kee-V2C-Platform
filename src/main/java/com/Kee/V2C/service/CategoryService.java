package com.Kee.V2C.service;

import com.Kee.V2C.dto.category.CategoryResponse;
import com.Kee.V2C.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponse> getCategoryByAttribute(String name,String description,Boolean active,Pageable page);
    Category getCategoryById(Long id);
    Page<Category> getAllCategories(Pageable page);

    CategoryResponse convertCategoryToDto(Category category);
}
