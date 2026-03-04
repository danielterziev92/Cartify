package com.cartify.ecommerce.category.mapper;

import com.cartify.ecommerce.category.dto.CategoryMetaDTO;
import com.cartify.ecommerce.category.model.CategoryMeta;
import com.cartify.ecommerce.category.response.CategoryMetaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMetaMapper {

    @Mapping(target = "categoryId", source = "category.id")
    CategoryMetaResponse toResponse(CategoryMeta categoryMeta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    CategoryMeta toEntity(CategoryMetaDTO dto);
}
