package com.cartify.ecommerce.category.mapper;

import com.cartify.ecommerce.category.dto.CategoryDTO;
import com.cartify.ecommerce.category.model.Category;
import com.cartify.ecommerce.category.model.CategoryFullView;
import com.cartify.ecommerce.category.response.CategoryFullViewResponse;
import com.cartify.ecommerce.category.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category category);

    CategoryFullViewResponse toFullViewResponse(CategoryFullView categoryFullView);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "meta", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "displayOrder", ignore = true)
    Category toEntity(CategoryDTO dto);
}
