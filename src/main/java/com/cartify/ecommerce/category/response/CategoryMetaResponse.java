package com.cartify.ecommerce.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryMetaResponse(
        Long id,
        String description,
        @JsonProperty("seo-title") String seoTitle,
        @JsonProperty("seo-description") String seoDescription,
        @JsonProperty("category-id") Long categoryId
) {
}
