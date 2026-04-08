package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.SubCategoryUpdateRequest;
import com.Kee.V2C.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {
    void updateSubCategoryFromDto(SubCategoryUpdateRequest subCategoryUpdateRequest,
                                  @MappingTarget SubCategory subCategory);
}
