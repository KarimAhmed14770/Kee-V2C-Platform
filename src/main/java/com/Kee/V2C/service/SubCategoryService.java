package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubCategoryService {
    SubCategoryResponse addSubCategory(SubCategoryAddRequest subCategoryAddRequest);
    SubCategoryResponse getSubCategoryById(Long id);
    SubCategoryResponse getSubCategoryByName(String name);
    Page<SubCategoryResponse> getByParentCategoryId(Long id,Pageable page);
    Page<SubCategoryResponse> getAllSubCategories(Pageable page);
    Page<SubCategoryResponse> getAllActiveSubCategories(Pageable page);
    Page<SubCategoryResponse> getAllInactiveSubCategories(Pageable page);
    Page<SubCategoryResponse> getSubCategoryByNameContains(String search,Pageable page);
    Page<SubCategoryResponse> getSubCategoryByDescriptionContains(String search,Pageable page);

    SubCategoryResponse updateSubCategory(Long id, SubCategoryUpdateRequest subCategoryUpdateRequest);
    SubCategoryResponse softDeleteSubCategory(Long id);
}
