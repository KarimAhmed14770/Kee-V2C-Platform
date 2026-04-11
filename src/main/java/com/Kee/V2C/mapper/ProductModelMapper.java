package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.product.ProductModelRequest;
import com.Kee.V2C.entity.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductModelMapper {
    void updateProductModelFromDto(ProductModelRequest productModelRequest, @MappingTarget ProductModel productModel);
}
