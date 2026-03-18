package com.cartify.ecommerce.shared.response;

import java.util.List;

public record PageResponse<T>(
        PageMetaResponse meta,
        List<T> data
) {
}
