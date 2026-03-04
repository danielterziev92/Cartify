package com.cartify.ecommerce.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryFullViewResponse(
        Long id,
        String name,
        String slug,
        @JsonProperty("image-url") String imageUrl,
        @JsonProperty("display-order") int displayOrder,
        String description,
        @JsonProperty("seo-title") String seoTitle,
        @JsonProperty("seo-description") String seoDescription,
        @JsonProperty("parent-id") Long parentId
) {
}
