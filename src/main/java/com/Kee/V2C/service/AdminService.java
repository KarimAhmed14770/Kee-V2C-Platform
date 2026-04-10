package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.Category;
import com.Kee.V2C.enums.ProductModelStatus;
import com.Kee.V2C.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    CustomerProfileResponse getCustomerProfileById(Long id);
    Page<CustomerProfileResponse> searchForCustomer(String userName, String email, String firstName,
                                                    String lastName, String shippingAddress,
                                                    UserStatus status,Pageable pageable);
    VendorProfileResponse getVendorProfileById(Long id);
    public Page<VendorProfileResponse> searchForVendor(String name, String description,
                                                       String address, UserStatus status,Float lowerRating,
                                                       Float higherRating,Pageable pageable);
    Page<CustomerProfileResponse> getAllCustomers(Pageable page);
    Page<VendorProfileResponse> getAllVendors( Pageable page);

    CustomerProfileResponse  modifyCustomerStatus(Long id, StatusUpdateDto status);

    VendorProfileResponse  modifyVendorStatus(Long id,StatusUpdateDto status);


    CategoryResponse addCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryProfileById(Long id);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest);
    CategoryResponse softDeleteCategory(Long id);
    Page<CategoryResponse> getAllCategories(Pageable page);
    Page<CategoryResponse> searchCategory(String name,String description,Boolean active,Pageable page);



    SubCategoryResponse addSubCategory(Long parentId,SubCategoryRequest subCategoryRequest);
    SubCategoryResponse getSubCategoryProfileById(Long id);
    SubCategoryResponse updateSubCategory(Long id, SubCategoryRequest subCategoryRequest);
    SubCategoryResponse softDeleteSubCategory(Long id);
    Page<SubCategoryResponse> getSubCategoriesOfParent(Long parentId,Pageable page);


    BrandResponse addBrand(BrandRequest brandRequest);
    BrandResponse getBrandById(Long id);
    Page<BrandResponse> getAllBrands(Pageable page);
    BrandResponse updateBrand(Long id, BrandRequest brandRequest);
    BrandResponse softDeleteBrand(Long id);


    ProductModelResponse addProductModel(ProductModelRequest productModelRequest);
    ProductModelResponse getProductModelById(Long id);
    Page<ProductModelResponse> getAllProductModels(Pageable page);
    Page<ProductModelResponse> getProductModelsByAttributes(String name, String description,
                                                            Long ownerId, Long subCategoryId, Long brandId,
                                                            Boolean isGlobal, ProductModelStatus status,
                                                            Pageable page);
    ProductModelResponse updateProductModel(Long id,ProductModelRequest productModelRequest);
    ProductModelResponse softDeleteProductModel(Long id);



}
