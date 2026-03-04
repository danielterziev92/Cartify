package com.cartify.ecommerce.category.dto;

import com.cartify.ecommerce.category.constants.CategoryConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CategoryDTO(
        @NotBlank(message = CategoryConstants.NAME_NOT_BLANK_MESSAGE)
        @Size(max = CategoryConstants.NAME_MAX_LENGTH, message = CategoryConstants.NAME_MAX_LENGTH_MESSAGE)
        String name,

        @NotBlank(message = CategoryConstants.SLUG_NOT_BLANK_MESSAGE)
        @Size(max = CategoryConstants.SLUG_MAX_LENGTH, message = CategoryConstants.SLUG_MAX_LENGTH_MESSAGE)
        String slug,

        @JsonProperty("image-url")
        String imageUrl,

        CategoryConstants status,

        @JsonProperty("display-order")
        Integer displayOrder,

        @JsonProperty("parent-id")
        @Min(value = CategoryConstants.PARENT_ID_MIN_VALUE, message = CategoryConstants.PARENT_ID_POSITIVE_MESSAGE)
        Long parentId
) {
}
