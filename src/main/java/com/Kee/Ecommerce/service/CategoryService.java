package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.CategoryAddRequest;
import com.Kee.Ecommerce.dto.CategoryUpdateRequest;
import com.Kee.Ecommerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category addCategory(CategoryAddRequest categoryRequest);
    Category getByID(Long id);
    Category getByName(String name);
    Page<Category> getAll(Pageable page);
    Page<Category> getAllActive(Pageable page);
    Page<Category> getAllInactive(Pageable page);
    Page<Category> getByNameContains(String search,Pageable page);
    Page<Category> getByDescriptionContains(String search,Pageable page);

    Category updateCategory(Long id, CategoryUpdateRequest categoryRequest);
    Category softDelete(Long id);
}
