package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.*;

public interface VendorService {
    VendorProfileResponse updateSellerProfile(VendorProfileRequest vendorProfileRequest);
    //ProductResponse addProduct(ProductRequest productRequest);
    VendorProfileResponse myProfile();
    //InventoryResponse addInventory(InventoryRequest inventoryRequest);
}
