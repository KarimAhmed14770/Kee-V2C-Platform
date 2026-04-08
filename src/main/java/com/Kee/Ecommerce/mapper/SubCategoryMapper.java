package com.Kee.Ecommerce.mapper;

import com.Kee.Ecommerce.dto.SubCategoryUpdateRequest;
import com.Kee.Ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {
    void updateSubCategoryFromDto(SubCategoryUpdateRequest subCategoryUpdateRequest,
                                  @MappingTarget SubCategory subCategory);
}
