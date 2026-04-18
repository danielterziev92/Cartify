package com.cartify.ecommerce.shared.domain.exception;

/**
 * Thrown when a domain value fails validation (e.g., a field is out of range or malformed).
 */
public class InvalidValueException extends DomainException {

    /**
     * Creates a new {@code InvalidValueException}.
     *
     * @param messageCode the i18n message key identifying the validation error
     * @param args        optional arguments interpolated into the resolved message
     */
    public InvalidValueException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
