package com.cartify.ecommerce.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponse(
        Long id,
        String name,
        @JsonProperty("parent-id") Long parentId
) {
}
