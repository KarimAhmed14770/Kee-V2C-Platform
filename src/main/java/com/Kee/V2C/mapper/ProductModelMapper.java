package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.product.ProductModelRegisterRequest;
import com.Kee.V2C.dto.product.ProductModelUpdateRequest;
import com.Kee.V2C.entity.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductModelMapper {
    void updateProductModelFromDto(ProductModelUpdateRequest productModelUpdateRequest, @MappingTarget ProductModel productModel);
}
