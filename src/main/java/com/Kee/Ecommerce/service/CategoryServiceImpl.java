package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.CategoryRepository;
import com.Kee.Ecommerce.dto.CategoryAddRequest;
import com.Kee.Ecommerce.dto.CategoryResponse;
import com.Kee.Ecommerce.dto.CategoryUpdateRequest;
import com.Kee.Ecommerce.entity.Category;
import com.Kee.Ecommerce.exception.CategoryNotFoundException;
import com.Kee.Ecommerce.exception.ResourceAlreadyExistsException;
import com.Kee.Ecommerce.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    @Transactional
    public CategoryResponse addCategory(CategoryAddRequest categoryRequest) {
        if(categoryRepository.existsByNameIgnoreCase(categoryRequest.name())){
            throw new ResourceAlreadyExistsException("category Already exists ");
        }
        Category category=new Category(categoryRequest.name(), categoryRequest.description(), categoryRequest.imageUrl(),
                categoryRequest.active());
        categoryRepository.save(category);

        return convertCategoryToDto(category);
    }

    /*read and create implemented , remaining update, delete*/

    @Override
    public CategoryResponse getCategoryById(Long id){
        return  convertCategoryToDto(categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found."))
        );
    }
    @Override
    public CategoryResponse getCategoryByName(String name){
        return  convertCategoryToDto(categoryRepository.findByName(name).orElseThrow(
                ()->new CategoryNotFoundException("Category: "+name+" Not found."))
        );
    }
    @Override
    public Page<CategoryResponse> getAllCategories(Pageable page){
        Page<Category> categoryPage= categoryRepository.findAll(page);
        return categoryPage.map(this::convertCategoryToDto);
    }


    @Override
    public Page<CategoryResponse> getAllActiveCategories(Pageable page) {

        Page<Category> categoryPage= categoryRepository.findAllByActiveTrue(page);
        return categoryPage.map(this::convertCategoryToDto);
    }
    @Override
    public Page<CategoryResponse> getAllInactiveCategories(Pageable page) {

        Page<Category> categoryPage= categoryRepository.findAllByActiveFalse(page);
        return categoryPage.map(this::convertCategoryToDto);
    }

    @Override
    public Page<CategoryResponse> getCategoryByNameContains(String search,Pageable page){
        Page<Category> categoryPage= categoryRepository.findByDescriptionContainingIgnoreCase(search,page);
        return categoryPage.map(this::convertCategoryToDto);
    }

    @Override
    public Page<CategoryResponse> getCategoryByDescriptionContains(String search,Pageable page){
        Page<Category> categoryPage=categoryRepository.findByDescriptionContainingIgnoreCase(search,page);
        return categoryPage.map(this::convertCategoryToDto);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest){
        Category updatedCategory=getById(id);
        categoryMapper.updateCategoryFromDto(categoryRequest,updatedCategory);
        categoryRepository.save(updatedCategory);

        return convertCategoryToDto(updatedCategory);
    }
    @Override
    @Transactional
    public CategoryResponse softDeleteCategory(Long id){
        Category category=getById(id);
        category.setActive(false);
        categoryRepository.save(category);
        return convertCategoryToDto(category);
    }

    private Category getById(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryNotFoundException("Category not found")
        );
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

