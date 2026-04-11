package com.Kee.V2C.dto;

public record AdminAdditionOnProductRequest(Long brandId,
                                            Long subcategoryId,
                                            String modifiedName,
                                            String modifiedDescription,
                                            Boolean modifiedGlobal
                                            ) {
}
