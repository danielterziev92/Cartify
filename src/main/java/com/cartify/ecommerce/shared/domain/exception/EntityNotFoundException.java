package com.cartify.ecommerce.shared.domain.exception;

/**
 * Thrown when a requested entity cannot be found in the system (e.g., lookup by ID returns no result).
 */
public class EntityNotFoundException extends DomainException {

    /**
     * Creates a new {@code EntityNotFoundException}.
     *
     * @param messageCode the i18n message key identifying the missing entity
     * @param args        optional arguments interpolated into the resolved message
     */
    public EntityNotFoundException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
