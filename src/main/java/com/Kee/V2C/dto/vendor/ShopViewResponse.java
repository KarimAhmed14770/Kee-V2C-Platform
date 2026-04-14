package com.Kee.V2C.dto.vendor;

import com.Kee.V2C.dto.product.ProductResponse;
import org.springframework.data.domain.Page;

public record ShopViewResponse(
        String imageUrl,
        ShopResponse shopResponse,
        Page<ProductResponse> productResponses
) {
}
