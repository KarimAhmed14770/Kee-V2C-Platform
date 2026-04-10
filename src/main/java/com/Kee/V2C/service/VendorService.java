package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;

interface VendorService {
    VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest);
    VendorProfileResponse myProfile();
    ShopResponse registerShop(ShopRequest shopRequest);
    ShopResponse updateShopInfo(Long id,ShopRequest shopRequest);
    ShopResponse deactivateShop(Long id);
    ShopResponse activateShop(Long id);
    ProductResponse addNewGlobalProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse addNewLocalProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse updateProductStock(GlobalProductAddToStockRequest globalProductAddToStockRequest) ;
    ProductResponse hideProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse showProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest);
}
