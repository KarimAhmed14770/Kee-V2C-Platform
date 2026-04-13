package com.Kee.V2C.rest;

import com.Kee.V2C.dto.*;
import com.Kee.V2C.dto.brand.BrandRequest;
import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.dto.category.*;
import com.Kee.V2C.dto.customer.CustomerProfileResponse;
import com.Kee.V2C.dto.product.ProductModelRequest;
import com.Kee.V2C.dto.product.ProductModelResponse;
import com.Kee.V2C.dto.product.ProductRequestResponse;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
import com.Kee.V2C.enums.ProductModelStatus;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.service.AdminService;
import com.Kee.V2C.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRegisterRequest categoryRegisterRequest){
        return ResponseEntity.ok(adminService.addCategory(categoryRegisterRequest));
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
                                                    , @RequestBody SubCategoryRegisterRequest subCategoryRegisterRequest){
        return ResponseEntity.ok(adminService.addSubCategory(id, subCategoryRegisterRequest));
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
                                                                 @RequestBody SubCategoryRegisterRequest subCategoryRequest){
        return ResponseEntity.ok(adminService.updateSubCategory(subCategoryId,subCategoryRequest));
    }

    @PatchMapping("/categories/{parentId}/subcategories/delete/{subCategoryId}")
    public ResponseEntity<SubCategoryResponse> softDeleteSubCategory(@PathVariable("subCategoryId") Long subCategoryId){
        return ResponseEntity.ok(adminService.softDeleteSubCategory(subCategoryId));
    }



    @PostMapping("/brands")
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest){
        BrandResponse brandResponse=adminService.addBrand(brandRequest);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(brandResponse.id())
                .toUri();
        //returning also the url path to find the created source to make it available for the frontend
        //so they don't have to reconstruct it
        return ResponseEntity.created(location).body(brandResponse);
    }

    @GetMapping("/brands")
    public ResponseEntity<Page<BrandResponse>> getBrands(Pageable page){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllBrands(page));
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body( adminService.getBrandById(id));
    }

    @PatchMapping("/brands/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable("id") Long id,@RequestBody BrandRequest brandRequest){
        return ResponseEntity.ok(adminService.updateBrand(id,brandRequest));
    }

    @PatchMapping("/brands/delete/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable("id")Long id){
        return ResponseEntity.ok(adminService.softDeleteBrand(id));
    }


    @PostMapping("/product-models")
    public ResponseEntity<ProductModelResponse> addProductModel(@RequestBody ProductModelRequest productModelRequest){
        ProductModelResponse response=adminService.addProductModel(productModelRequest);
        URI location=ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/product-models")
    public ResponseEntity<Page<ProductModelResponse>> getAllProductModels(Pageable page){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllProductModels(page));
    }

    @GetMapping("/product-models/{id}")
    public ResponseEntity<ProductModelResponse> getProductModelById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getProductModelById(id));
    }

    @GetMapping("/product-models/search")
    public ResponseEntity<Page<ProductModelResponse>> getProductModelByAttribute(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Boolean isGlobal,
            @RequestParam(required = false) ProductModelStatus status,
            Pageable page){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getProductModelsByAttributes
                (name,description,ownerId,subCategoryId,brandId,isGlobal,status,page));
    }

    @PatchMapping("/product-models/{id}")
    public ResponseEntity<ProductModelResponse> updateProductModel(@PathVariable("id") Long id,
                                                                   @RequestBody ProductModelRequest productModelRequest){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateProductModel(id,productModelRequest));
    }

    @PatchMapping("/product-models/delete/{id}")
    public ResponseEntity<ProductModelResponse> softDeleteProductModel(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.softDeleteProductModel(id));
    }


    @GetMapping("/product-requests")
    public ResponseEntity<Page<ProductRequestResponse>> getAllProductsRequests(Pageable page){
        return ResponseEntity.ok(adminService.getAllProductsRequests(page));
    }

    @GetMapping("/product-requests/pending")
    public ResponseEntity<Page<ProductRequestResponse>> getPendingVendorsProductsRequests(Pageable page){
        return ResponseEntity.ok(adminService.getPendingVendorsProductsRequests(page));
    }

    @GetMapping("/product-requests/{id}")
    public ResponseEntity<ProductRequestResponse> viewProductAddRequest(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.viewProductAddRequest(id));
    }

    @PatchMapping("/product-requests/reject/{id}")
    public ResponseEntity<ProductRequestResponse> rejectProductRequest(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.rejectProductAddRequest(id));
    }

    @PostMapping("/product-requests/process/{id}")
    public ResponseEntity<ProductModelResponse> processProductRequest(@PathVariable("id") Long id,
                                                                        @RequestBody AdminAdditionOnProductRequest adminAdditionOnProductRequest){
        return ResponseEntity.ok(adminService.processProductAddRequest(id,adminAdditionOnProductRequest));
    }


}
