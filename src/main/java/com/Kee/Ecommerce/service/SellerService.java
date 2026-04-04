package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.ProductRequest;
import com.Kee.Ecommerce.dto.ProductResponse;
import com.Kee.Ecommerce.dto.SellerProfileRequest;

public interface SellerService {
    void updateSellerProfile(SellerProfileRequest sellerProfileRequest);
    ProductResponse addProduct(ProductRequest productRequest);
}
