package com.Kee.V2C.dto.product;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              Long ProductModelId,
                              Long CategoryId,
                              Long shopId,
                              String name,
                              String description,
                              BigDecimal price,
                              Integer stock
                            , String imageUrl,
                              Boolean active
                              ) { }
