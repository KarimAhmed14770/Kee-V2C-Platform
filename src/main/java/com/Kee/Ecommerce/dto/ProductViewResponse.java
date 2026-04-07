package com.Kee.Ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductViewResponse(Long id,
                                  String name,
                                  String description,
                                  BigDecimal price,
                                  Integer stock,
                                  String imageUrl,
                                  String Category) {}
