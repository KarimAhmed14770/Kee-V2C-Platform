package com.Kee.V2C.service.Admin;

import com.Kee.V2C.dto.Authentication.StatusUpdateDto;
import com.Kee.V2C.dto.brand.BrandRegisterRequest;
import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.dto.brand.BrandUpdateRequest;
import com.Kee.V2C.dto.category.*;
import com.Kee.V2C.dto.customer.CustomerProfileResponse;
import com.Kee.V2C.dto.product.*;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
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


    CategoryResponse addCategory(CategoryRegisterRequest categoryRegisterRequest);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest);
    CategoryResponse softDeleteCategory(Long id);



    SubCategoryResponse addSubCategory(Long parentId, SubCategoryRegisterRequest subCategoryRegisterRequest);
    SubCategoryResponse updateSubCategory(Long id, SubCategoryUpdateRequest subCategoryRequest);
    SubCategoryResponse softDeleteSubCategory(Long id);


    BrandResponse addBrand(BrandRegisterRequest brandRegisterRequest);
    BrandResponse updateBrand(Long id, BrandUpdateRequest brandUpdateRequest);
    BrandResponse softDeleteBrand(Long id);


    ProductModelResponse addProductModel(ProductModelRegisterRequest productModelRegisterRequest);
    ProductModelResponse updateProductModel(Long id, ProductModelUpdateRequest productModelUpdateRequest);
    ProductModelResponse softDeleteProductModel(Long id);
    Page<ProductModelResponse> searchProductModel(String name, String description, Long ownerId,
                                                  Long subCategoryId, Long brandId, Boolean isGlobal,
                                                  ProductModelStatus status, Pageable page);


    Page<ProductRequestResponse> getAllProductsRequests(Pageable page);
    Page<ProductRequestResponse> getPendingVendorsProductsRequests(Pageable page);
    ProductRequestResponse viewProductAddRequest(Long id);
    ProductRequestResponse rejectProductAddRequest(Long id);
    ProductModelResponse processProductAddRequest(Long requestId, AdminAdditionOnProductRequest adminAdditionOnProductRequest);



}
