package com.Kee.V2C.rest;

import com.Kee.V2C.dto.*;
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
        return ResponseEntity.ok(adminService.getCustomerById(id));
    }


    @GetMapping("/customers/search")
    public ResponseEntity<CustomerProfileResponse> getCustomerByAttribute
            (@RequestParam(required = false)String firstName,
             @RequestParam(required = false)String lastName,
             @RequestParam(required = false)String userName,
             @RequestParam(required = false)String email,
             @SortDefault(sort="firstName",direction = Sort.Direction.ASC)Sort sort){
        return ResponseEntity.ok(adminService.getCustomerByEmail(email));
    }

    @GetMapping("/vendors")
    public ResponseEntity<Page<VendorProfileResponse>> getAllVendors(Pageable page){
        return ResponseEntity.ok(adminService.getAllVendors(page));
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

    @GetMapping("/Categories/id/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @GetMapping("/Categories/name/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable("name") String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }




}
