package com.Kee.V2C.dto;

import java.math.BigDecimal;
import java.util.List;

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
