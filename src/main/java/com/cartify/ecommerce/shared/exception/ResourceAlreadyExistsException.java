package com.cartify.ecommerce.shared.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {
    private final String message;
    private final Object[] args;

    public ResourceAlreadyExistsException(String message, Object... args) {
        this.message = message;
        this.args = args;
    }
}
