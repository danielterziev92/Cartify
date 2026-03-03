package com.cartify.ecommerce.category;

import java.util.List;

public record CategoryResponse(Long id, String name, List<CategoryResponse> children) {
}
