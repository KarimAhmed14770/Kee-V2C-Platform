package com.Kee.V2C.dto.product;

import java.math.BigDecimal;

public record ProductViewResponse(Long id,
                                  String name,
                                  String description,
                                  BigDecimal price,
                                  Integer stock,
                                  String ProductModelImageUrl,
                                  String ProductImageUrl,
                                  String Category) {}
