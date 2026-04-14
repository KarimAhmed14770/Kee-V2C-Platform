package com.Kee.V2C.service;

import com.Kee.V2C.dto.product.*;
import com.Kee.V2C.dto.vendor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface VendorService {
    VendorProfileResponse updateVendorProfile(VendorUpdateProfileRequest vendorUpdateProfileRequest);
    VendorProfileResponse myProfile();
    ShopResponse registerShop(ShopRegisterRequest shopRegisterRequest);
    ShopResponse updateShopInfo(ShopUpdateRequest shopRequest);
    ShopResponse deactivateShop();
    ShopResponse activateShop();
    ShopViewResponse viewShop(Pageable page);
    Page<ProductModelResponse> searchGlobalProductModel(Long brandId, Long subCategoryId, String description, Pageable page);
    Page<ProductModelResponse> myProductModels(Pageable page) ;
    ProductRequestResponse requestNewProduct(NewProductRequest newProductRequest);
    ProductResponse addProductToStock(ProductAddToStockRequest productAddToStockRequest);
    Page<ProductResponse> showMyProducts(Pageable page);
    ProductResponse updateProductInfo(Long id,ProductUpdateRequest productUpdateRequest);
    ProductResponse addStock(Long id,Integer quantity);


}
