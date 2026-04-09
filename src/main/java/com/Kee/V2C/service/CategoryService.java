package com.Kee.V2C.service;

import com.Kee.V2C.dto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponse> getCategoryByAttribute(String name,String description,Boolean active,Pageable page);
}
