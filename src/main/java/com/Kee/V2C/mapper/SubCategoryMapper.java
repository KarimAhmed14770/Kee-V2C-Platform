package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.category.SubCategoryRegisterRequest;
import com.Kee.V2C.dto.category.SubCategoryUpdateRequest;
import com.Kee.V2C.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {
    void updateSubCategoryFromDto(SubCategoryUpdateRequest subCategoryRequest,
                                  @MappingTarget SubCategory subCategory);
}
