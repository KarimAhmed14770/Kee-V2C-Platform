package com.Kee.V2C.dto;

import java.math.BigDecimal;

public record ProductViewResponse(Long id,
                                  String name,
                                  String description,
                                  BigDecimal price,
                                  Integer stock,
                                  String imageUrl,
                                  String Category) {}
