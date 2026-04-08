package com.Kee.V2C.service;

import com.Kee.V2C.Repository.CategoryRepository;
import com.Kee.V2C.Repository.SubCategoryRepository;
import com.Kee.V2C.dto.SubCategoryAddRequest;
import com.Kee.V2C.dto.SubCategoryResponse;
import com.Kee.V2C.dto.SubCategoryUpdateRequest;
import com.Kee.V2C.entity.Category;
import com.Kee.V2C.entity.SubCategory;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.mapper.SubCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class SubCategoryServiceImpl implements SubCategoryService{
        private final SubCategoryRepository subCategoryRepository;
        private final CategoryRepository categoryRepository;
        private final SubCategoryMapper subCategoryMapper;

        @Autowired
        public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository
                ,CategoryRepository categoryRepository,SubCategoryMapper subCategoryMapper){
            this.subCategoryRepository=subCategoryRepository;
            this.categoryRepository=categoryRepository;
            this.subCategoryMapper=subCategoryMapper;
        }
        @Override @Transactional
        public SubCategoryResponse addSubCategory(SubCategoryAddRequest subCategoryAddRequest) {
            if(subCategoryRepository.existsByNameIgnoreCase(subCategoryAddRequest.name())){
                throw new ResourceAlreadyExistsException("sub-category Already exists ");
            }
            Category parentCategory=categoryRepository.findById(subCategoryAddRequest.parentId())
                    .orElseThrow(()-> new ResourceNotFoundException("parent category not found"));
            SubCategory subCategory=new SubCategory(
                    parentCategory,
                    subCategoryAddRequest.name(),
                    subCategoryAddRequest.description(),
                    subCategoryAddRequest.imageUrl(),
                    subCategoryAddRequest.active());
            parentCategory.addSubcategory(subCategory);//linking parent to sub

            subCategoryRepository.save(subCategory);

            return convertSubCategoryToDto(subCategory);
    }

        @Override
        public SubCategoryResponse getSubCategoryById(Long id){
        return convertSubCategoryToDto(getById(id));
    }

        @Override
        public SubCategoryResponse getSubCategoryByName(String name){
        return convertSubCategoryToDto(subCategoryRepository.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("subcategory with name: "
                +name+" not found.")));
    }

        @Override
        public Page<SubCategoryResponse> getByParentCategoryId(Long id,Pageable page){
            Page<SubCategory> subCategories=subCategoryRepository.findByParentCategoryId(id,page);
            return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        public Page<SubCategoryResponse> getAllSubCategories(Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findAll(page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        public Page<SubCategoryResponse> getAllActiveSubCategories(Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findAllByActiveTrue(page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        public Page<SubCategoryResponse> getAllInactiveSubCategories(Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findAllByActiveFalse(page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        public Page<SubCategoryResponse> getSubCategoryByNameContains(String search,Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findByNameContainingIgnoreCase(search,page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        public Page<SubCategoryResponse> getSubCategoryByDescriptionContains(String search,Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findByDescriptionContainingIgnoreCase(search,page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

        @Override
        @Transactional
        public SubCategoryResponse updateSubCategory(Long id, SubCategoryUpdateRequest subCategoryUpdateRequest){
            SubCategory updatedCategory=getById(id);
            subCategoryMapper.updateSubCategoryFromDto(subCategoryUpdateRequest,updatedCategory);
            subCategoryRepository.save(updatedCategory);
            return convertSubCategoryToDto(updatedCategory);
        }

    @Override
    @Transactional
    public SubCategoryResponse softDeleteSubCategory(Long id){
            SubCategory subCategory=getById(id);
            subCategory.setActive(false);
            subCategoryRepository.save(subCategory);
        return convertSubCategoryToDto(subCategory);
    }

    private SubCategory getById(Long id){
            return subCategoryRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("subcategory with id: "+id+" not found."));
    }

    private SubCategoryResponse convertSubCategoryToDto(SubCategory subCategory){
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

