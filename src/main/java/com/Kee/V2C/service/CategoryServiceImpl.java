package com.Kee.V2C.service;

import com.Kee.V2C.Repository.CategoryRepository;
import com.Kee.V2C.dto.CategoryResponse;
import com.Kee.V2C.entity.Category;
import com.Kee.V2C.exception.CategoryNotFoundException;
import com.Kee.V2C.mapper.CategoryMapper;
import com.Kee.V2C.specifications.CategorySpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,CategoryMapper categoryMapper){
        this.categoryRepository=categoryRepository;
        this.categoryMapper=categoryMapper;
    }

    @Override
    public Page<CategoryResponse> getCategoryByAttribute(String name,String description,
                                                         Boolean active,Pageable page){
        Specification<Category> spec=(root, query, cb) -> cb.conjunction() ;
        if(name!=null) spec=spec.and(CategorySpecs.hasName(name));
        if(description!=null) spec=spec.and(CategorySpecs.hasDescription(description));
        if(active!=null) spec=spec.and(CategorySpecs.hasActive(active));

        Page<Category> categories=categoryRepository.findAll(spec,page);

        return  categories.map(this::convertCategoryToDto);
    }


    private CategoryResponse convertCategoryToDto(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImageUrl(),
                category.isActive()
        );
    }
}

