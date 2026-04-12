package com.Kee.V2C.mapper;


import com.Kee.V2C.dto.product.ProductUpdateRequest;
import com.Kee.V2C.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    void updateProductFromDto(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product);
}
