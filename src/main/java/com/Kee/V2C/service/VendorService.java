package com.Kee.V2C.service;

import com.Kee.V2C.dto.product.*;
import com.Kee.V2C.dto.vendor.ShopRequest;
import com.Kee.V2C.dto.vendor.ShopResponse;
import com.Kee.V2C.dto.vendor.VendorProfileRequest;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface VendorService {
    VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest);
    VendorProfileResponse myProfile();
    ShopResponse registerShop(ShopRequest shopRequest);
    ShopResponse updateShopInfo(ShopRequest shopRequest);
    ShopResponse deactivateShop();
    ShopResponse activateShop();
    ShopResponse viewShop();
    Page<ProductModelResponse> searchGlobalProductModel(Long brandId, Long subCategoryId, String description, Pageable page);
    Page<ProductModelResponse> myProductModels(Pageable page) ;
    ProductRequestResponse requestNewProduct(NewProductRequest newProductRequest);
    ProductResponse addProductToStock(ProductAddToStockRequest productAddToStockRequest);


}
