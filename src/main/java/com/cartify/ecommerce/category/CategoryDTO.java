package com.cartify.ecommerce.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CategoryDTO(
        @NotBlank(message = CategoryConstants.NAME_NOT_BLANK_MESSAGE)
        @Size(max = CategoryConstants.NAME_MAX_LENGTH, message = CategoryConstants.NAME_MAX_LENGTH_MESSAGE)
        String name,

        @Min(value = CategoryConstants.PARENT_ID_MIN_VALUE, message = CategoryConstants.PARENT_ID_POSITIVE_MESSAGE)
        Long parentId
) {
}
