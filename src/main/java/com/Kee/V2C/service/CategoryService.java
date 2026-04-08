package com.Kee.V2C.service;

import com.Kee.V2C.dto.CategoryAddRequest;
import com.Kee.V2C.dto.CategoryResponse;
import com.Kee.V2C.dto.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse addCategory(CategoryAddRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse getCategoryByName(String name);
    Page<CategoryResponse> getAllCategories(Pageable page);
    Page<CategoryResponse> getAllActiveCategories(Pageable page);
    Page<CategoryResponse> getAllInactiveCategories(Pageable page);
    Page<CategoryResponse> getCategoryByNameContains(String search,Pageable page);
    Page<CategoryResponse> getCategoryByDescriptionContains(String search,Pageable page);

    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest);
    CategoryResponse softDeleteCategory(Long id);
}
