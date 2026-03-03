package com.cartify.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

public record PageMetaResponse(
        @JsonProperty("current-page") int currentPage,
        @JsonProperty("per-page") int perPage,
        long total,
        @JsonProperty("last-page") int lastPage
) {
    @Contract("_ -> new")
    public static @NotNull PageMetaResponse from(@NotNull Page<?> page) {
        return new PageMetaResponse(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
