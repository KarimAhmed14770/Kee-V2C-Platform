package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.ProductRequest;
import com.Kee.Ecommerce.dto.ProductResponse;
import com.Kee.Ecommerce.dto.SellerProfileRequest;
import com.Kee.Ecommerce.dto.SellerProfileResponse;

public interface SellerService {
    SellerProfileResponse updateSellerProfile(SellerProfileRequest sellerProfileRequest);
    ProductResponse addProduct(ProductRequest productRequest);
}
