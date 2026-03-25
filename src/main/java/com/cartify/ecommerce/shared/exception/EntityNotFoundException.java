package com.cartify.ecommerce.shared.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String message;
    private final Object[] args;

    public EntityNotFoundException(String message, Object... args) {
        this.message = message;
        this.args = args;
    }
}
