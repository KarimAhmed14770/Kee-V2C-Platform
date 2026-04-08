package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.CategoryRepository;
import com.Kee.Ecommerce.dto.CategoryAddRequest;
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
    public Category addCategory(CategoryAddRequest categoryRequest) {
        if(categoryRepository.existsByNameIgnoreCase(categoryRequest.name())){
            throw new ResourceAlreadyExistsException("category Already exists ");
        }
        Category category=new Category(categoryRequest.name(), categoryRequest.description(), categoryRequest.imageUrl(),
                categoryRequest.active());
        categoryRepository.save(category);

        return category;
    }

    /*read and create implemented , remaining update, delete*/

    @Override
    public Category getByID(Long id){
        return  categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found.")
        );
    }
    @Override
    public Category getByName(String name){
        return  categoryRepository.findByName(name).orElseThrow(
                ()->new CategoryNotFoundException("Category: "+name+" Not found.")
        );
    }
    @Override
    public Page<Category> getAll(Pageable page){
        return categoryRepository.findAll(page);
    }


    @Override
    public Page<Category> getAllActive(Pageable page) {

        return categoryRepository.findAllByActiveTrue(page);
    }
    @Override
    public Page<Category> getAllInactive(Pageable page) {

        return categoryRepository.findAllByActiveFalse(page);
    }

    @Override
    public Page<Category> getByNameContains(String search,Pageable page){
        return categoryRepository.findByDescriptionContainingIgnoreCase(search,page);
    }

    @Override
    public Page<Category> getByDescriptionContains(String search,Pageable page){
        return categoryRepository.findByDescriptionContainingIgnoreCase(search,page);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryUpdateRequest categoryRequest){
        Category updatedCategory=getByID(id);
        categoryMapper.updateCategoryFromDto(categoryRequest,updatedCategory);
        categoryRepository.save(updatedCategory);

        return updatedCategory;
    }
    @Override
    @Transactional
    public Category softDelete(Long id){
        Category category=getByID(id);
        category.setActive(false);
        categoryRepository.save(category);
        return category;
    }
}

