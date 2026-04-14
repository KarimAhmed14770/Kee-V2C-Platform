package com.Kee.V2C.rest;


import com.Kee.V2C.dto.category.CategoryResponse;
import com.Kee.V2C.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private  final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable page){
        return ResponseEntity.ok(categoryService.getAllCategories(page).map(categoryService::convertCategoryToDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.convertCategoryToDto(categoryService.getCategoryProfileById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryResponse>> getCategoryByAttribute(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active,
            Pageable page){
        return ResponseEntity.ok(categoryService.getCategoryByAttribute(name,description,active,page));
    }
}
