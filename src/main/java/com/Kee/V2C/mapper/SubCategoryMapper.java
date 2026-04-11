package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.category.SubCategoryRequest;
import com.Kee.V2C.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {
    void updateSubCategoryFromDto(SubCategoryRequest subCategoryRequest,
                                  @MappingTarget SubCategory subCategory);
}
