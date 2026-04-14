package com.Kee.V2C.service.Category;

import com.Kee.V2C.dto.category.SubCategoryResponse;
import com.Kee.V2C.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubCategoryService {
    SubCategory getSubCategoryById(Long id);
    Page<SubCategory> getSubCategoriesOfParent(Long parentId, Pageable page);
    SubCategoryResponse convertSubCategoryToDto(SubCategory subCategory);

}
