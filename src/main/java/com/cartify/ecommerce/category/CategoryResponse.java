package com.cartify.ecommerce.category;

public record CategoryResponse(Long id, String name, CategoryResponse parent) {
}
