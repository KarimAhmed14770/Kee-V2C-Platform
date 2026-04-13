package com.Kee.V2C.mapper;



import com.Kee.V2C.dto.brand.BrandRegisterRequest;
import com.Kee.V2C.dto.brand.BrandUpdateRequest;
import com.Kee.V2C.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrandMapper {
    @Mapping(target = "imageUrl",ignore = true)
    void updateBrandFromDto(BrandUpdateRequest brandUpdateRequest, @MappingTarget Brand brand);

}
