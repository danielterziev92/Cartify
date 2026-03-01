package com.cartify.ecommerce.payload;

import java.util.List;

public record PageResponse<T>(
        PageMetaResponse meta,
        List<T> data
) {
}
