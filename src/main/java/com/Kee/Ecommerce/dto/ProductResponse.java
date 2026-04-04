package com.Kee.Ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              BigDecimal price,
                              Integer stock
                            , String imageUrl,
                              String Category,
                              Boolean active
                              ) { }
