package com.Kee.V2C.dto.product;

import java.math.BigDecimal;

public record ProductUpdateRequest (String name,
                                    String description,
                                    String image_url,
                                    BigDecimal price){
}
