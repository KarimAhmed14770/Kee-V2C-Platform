package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;

public interface VendorService {
    VendorProfileResponse updateSellerProfile(VendorProfileRequest vendorProfileRequest);
    //ProductResponse addProduct(ProductRequest productRequest);
    VendorProfileResponse myProfile();
    //InventoryResponse addInventory(InventoryRequest inventoryRequest);
}
