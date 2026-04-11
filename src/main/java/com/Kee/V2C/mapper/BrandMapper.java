package com.Kee.V2C.mapper;



import com.Kee.V2C.dto.brand.BrandRequest;
import com.Kee.V2C.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrandMapper {
    void updateBrandFromDto(BrandRequest brandRequest, @MappingTarget Brand brand);

}
