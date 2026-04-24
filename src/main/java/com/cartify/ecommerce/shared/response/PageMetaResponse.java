package com.cartify.ecommerce.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;

public record PageMetaResponse(
        @JsonProperty("current-page") int currentPage,
        @JsonProperty("last-page") int lastPage,
        @JsonProperty("item-per-page") int itemPerPage,
        @JsonProperty("item-total") long itemTotal
) {

    public static @NonNull PageMetaResponse from(@NonNull Page<?> page) {
        return new PageMetaResponse(
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
