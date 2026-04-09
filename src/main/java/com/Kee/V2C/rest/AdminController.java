package com.Kee.V2C.rest;

import com.Kee.V2C.dto.*;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.service.AdminService;
import com.Kee.V2C.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final CategoryService categoryService;

    @Autowired
    public  AdminController( AdminService adminService,CategoryService categoryService){
        this.adminService=adminService;
        this.categoryService=categoryService;
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerProfileResponse>> getAllCustomers(Pageable page){
        return ResponseEntity.ok(adminService.getAllCustomers(page));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerProfileResponse> getCustomerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getCustomerProfileById(id));
    }


    @GetMapping("/customers/search")
    public ResponseEntity<Page<CustomerProfileResponse>> getCustomerByAttribute
            (@RequestParam(required = false)String firstName,
             @RequestParam(required = false)String lastName,
             @RequestParam(required = false)String userName,
             @RequestParam(required = false)String email,
             @RequestParam(required = false)String address,
             @RequestParam(required = false) UserStatus status,
             Pageable page,
             @SortDefault(sort="firstName",direction = Sort.Direction.ASC)Sort sort){
        return ResponseEntity.ok(adminService.searchForCustomer(userName,email,firstName,lastName,
                                            address,status,page));
    }

    @PatchMapping("customers/modify-status/{id}")
    public CustomerProfileResponse modifyCustomerStatus(@PathVariable("id") Long id,
                                                        @RequestBody StatusUpdateDto status){
        return adminService.modifyCustomerStatus(id,status);
    }



    @GetMapping("/vendors")
    public ResponseEntity<Page<VendorProfileResponse>> getAllVendors(Pageable page){
        return ResponseEntity.ok(adminService.getAllVendors(page));
    }
    @GetMapping("/vendors/{id}")
    public ResponseEntity<VendorProfileResponse> getVendorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getVendorProfileById(id));
    }

    @GetMapping("/vendors/search")
    public ResponseEntity<Page<VendorProfileResponse>> getVendorByAttribute(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) Float lowerRating,
            @RequestParam(required = false) Float higherRating,
            Pageable page){
        return ResponseEntity.ok(adminService.searchForVendor(name,description,address,status
                ,lowerRating,higherRating,page));
    }

    @PatchMapping("vendors/modify-status/{id}")
    public VendorProfileResponse modifyVendorStatus(@PathVariable("id") Long id,
                                                        @RequestBody StatusUpdateDto status){
        return adminService.modifyVendorStatus(id,status);
    }





    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(adminService.addCategory(categoryRequest));
    }


    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable page){
        return ResponseEntity.ok(adminService.getAllCategories(page));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getCategoryProfileById(id));
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
                                                           @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return ResponseEntity.ok(adminService.updateCategory(id,categoryUpdateRequest));
    }

    @GetMapping("/categories/search")
    public ResponseEntity<Page<CategoryResponse>> getCategoryByAttribute(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active,
            Pageable page){
        return ResponseEntity.ok(adminService.searchCategory(name,description,active,page));
    }

    @PatchMapping("/categories/delete/{id}")
    public ResponseEntity<CategoryResponse> softDelete(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.softDeleteCategory(id));
    }






    @PostMapping("/categories/{id}/subcategories")
    public ResponseEntity<SubCategoryResponse> addSubCategory(@PathVariable("id") Long id
                                                    ,@RequestBody SubCategoryRequest subCategoryRequest){
        return ResponseEntity.ok(adminService.addSubCategory(id,subCategoryRequest));
    }

    @GetMapping("/categories/{id}/subcategories")
    public ResponseEntity<Page<SubCategoryResponse>> getSubCategoriesOfParent(@PathVariable("id") Long id,Pageable page){
        return ResponseEntity.ok(adminService.getSubCategoriesOfParent(id,page));
    }

    @GetMapping("/categories/{parentId}/subcategories/{subCategoryId}")
    public ResponseEntity<SubCategoryResponse> getSubCategoryById(@PathVariable("subCategoryId") Long subCategoryId){
        return ResponseEntity.ok(adminService.getSubCategoryProfileById(subCategoryId));
    }

    @PatchMapping("/categories/{parentId}/subcategories/{subCategoryId}")
    public ResponseEntity<SubCategoryResponse> updateSubCategory(@PathVariable("subCategoryId") Long subCategoryId,
                                                                 @RequestBody SubCategoryRequest subCategoryRequest){
        return ResponseEntity.ok(adminService.updateSubCategory(subCategoryId,subCategoryRequest));
    }

    @PatchMapping("/categories/{parentId}/subcategories/delete/{subCategoryId}")
    public ResponseEntity<SubCategoryResponse> softDeleteSubCategory(@PathVariable("subCategoryId") Long subCategoryId){
        return ResponseEntity.ok(adminService.softDeleteSubCategory(subCategoryId));
    }








}
