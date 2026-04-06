package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.*;

public interface SellerService {
    SellerProfileResponse updateSellerProfile(SellerProfileRequest sellerProfileRequest);
    ProductResponse addProduct(ProductRequest productRequest);
    SellerProfileResponse myProfile();
    InventoryResponse addInventory(InventoryRequest inventoryRequest);
}
