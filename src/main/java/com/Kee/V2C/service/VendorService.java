package com.Kee.V2C.service;

import com.Kee.V2C.dto.product.NewProductRequest;
import com.Kee.V2C.dto.product.ProductRequestResponse;
import com.Kee.V2C.dto.vendor.ShopRequest;
import com.Kee.V2C.dto.vendor.ShopResponse;
import com.Kee.V2C.dto.vendor.VendorProfileRequest;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;

interface VendorService {
    VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest);
    VendorProfileResponse myProfile();
    ShopResponse registerShop(ShopRequest shopRequest);
    ShopResponse updateShopInfo(ShopRequest shopRequest);
    ShopResponse deactivateShop();
    ShopResponse activateShop();
    ShopResponse viewShop();
    ProductRequestResponse requestNewProduct(NewProductRequest newProductRequest);
    /*
    ProductResponse requestNewLocalProduct(LocalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse addNewProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse updateProductStock(GlobalProductAddToStockRequest globalProductAddToStockRequest) ;
    ProductResponse hideProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest);
    ProductResponse showProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest);

     */
}
