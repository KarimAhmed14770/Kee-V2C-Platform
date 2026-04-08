package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.service.AdminService;
import com.Kee.Ecommerce.service.AdminServiceImpl;
import com.Kee.Ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("/vendors")
    public ResponseEntity<Page<VendorProfileResponse>> getAllVendors(Pageable page){
        return ResponseEntity.ok(adminService.getAllVendors(page));
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<CustomerProfileResponse> getCustomerByMail(@PathVariable("email")String email){
        return ResponseEntity.ok(adminService.getCustomerByEmail(email));
    }

    @GetMapping("/customers/id/{id}")
    public ResponseEntity<CustomerProfileResponse> getCustomerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getCustomerById(id));
    }

    @GetMapping("/customers/user_name/{userName}")
    public ResponseEntity<CustomerProfileResponse> getCustomerByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(adminService.getCustomerByUsername(userName));
    }

    @GetMapping("/vendors/email/{email}")
    public ResponseEntity<VendorProfileResponse> getVendorByMail(@PathVariable("email")String email){
        return ResponseEntity.ok(adminService.getVendorByEmail(email));
    }

    @GetMapping("/vendors/id/{id}")
    public ResponseEntity<VendorProfileResponse> getVendorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getVendorById(id));
    }

    @GetMapping("/vendors/user_name/{userName}")
    public ResponseEntity<VendorProfileResponse> getVendorByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(adminService.getVendorByUsername(userName));
    }


    @GetMapping("/Categories")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable page){
        return ResponseEntity.ok(categoryService.getAllCategories(page));
    }

    @PostMapping("/Categories")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryAddRequest categoryAddRequest){
        return ResponseEntity.ok(categoryService.addCategory(categoryAddRequest));
    }

    @PatchMapping("/Categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
                                                           @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryUpdateRequest));
    }

    @GetMapping("/Categories/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @GetMapping("/Categories/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable("name") String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }




}
