package com.Kee.V2C.rest;


import com.Kee.V2C.dto.category.CategoryResponse;
import com.Kee.V2C.dto.category.SubCategoryResponse;
import com.Kee.V2C.service.Category.CategoryService;
import com.Kee.V2C.service.Category.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, SubCategoryService subCategoryService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable page) {
        return ResponseEntity.ok(categoryService.getActiveCategories(page).map(categoryService::convertCategoryToDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.convertCategoryToDto(categoryService.getCategoryById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryResponse>> getCategoryByAttribute(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active,
            Pageable page) {
        return ResponseEntity.ok(categoryService.getCategoryByAttribute(name, description, active, page));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<Page<SubCategoryResponse>> getAllSubCategories(@PathVariable("id") Long id, Pageable page) {
        return ResponseEntity.ok(subCategoryService.getActiveSubCategoriesOfParent(id, page).map(subCategoryService::convertSubCategoryToDto));
    }

    @GetMapping("/{id}/subcategories/{subCategoryId}")
    public ResponseEntity<SubCategoryResponse> getSubCategoryById(@PathVariable("subCategoryId") Long id) {
        return ResponseEntity.ok(subCategoryService.convertSubCategoryToDto(subCategoryService.getSubCategoryById(id)));
    }


}
