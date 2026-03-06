package com.cartify.ecommerce.category.dto;

import com.cartify.ecommerce.category.constants.CategoryMetaConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record CategoryMetaDTO(
        String description,

        @JsonProperty("seo-title")
        @Size(max = CategoryMetaConstants.SEO_TITLE_MAX_LENGTH, message = CategoryMetaConstants.SEO_TITLE_MAX_LENGTH_MESSAGE)
        String seoTitle,

        @JsonProperty("seo-description")
        @Size(max = CategoryMetaConstants.SEO_DESCRIPTION_MAX_LENGTH, message = CategoryMetaConstants.SEO_DESCRIPTION_MAX_LENGTH_MESSAGE)
        String seoDescription
) {
}
