package com.Kee.V2C.service.Category;

import com.Kee.V2C.Repository.SubCategoryRepository;
import com.Kee.V2C.dto.category.SubCategoryResponse;
import com.Kee.V2C.entity.SubCategory;
import com.Kee.V2C.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository){
        this.subCategoryRepository=subCategoryRepository;
    }

    @Override
    public SubCategory getSubCategoryById(Long id){
        return subCategoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("sub-category with id: "+id+" not found."));

    }

    @Override
    public Page<SubCategory> getSubCategoriesOfParent(Long parentId, Pageable page){
        return subCategoryRepository.findByParentId(parentId,page);
    }

    public SubCategoryResponse convertSubCategoryToDto(SubCategory subCategory){
        return new SubCategoryResponse(
                subCategory.getParentCategory().getId(),
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getDescription(),
                subCategory.getImageUrl(),
                subCategory.isActive()
        );
    }
}

