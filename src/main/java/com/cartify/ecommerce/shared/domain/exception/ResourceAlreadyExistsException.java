package com.cartify.ecommerce.shared.domain.exception;

/**
 * Thrown when an attempt is made to create a resource that already exists (e.g., duplicate email or slug).
 */
public class ResourceAlreadyExistsException extends DomainException {

    /**
     * Creates a new {@code ResourceAlreadyExistsException}.
     *
     * @param messageCode the i18n message key identifying the conflict
     * @param args        optional arguments interpolated into the resolved message
     */
    public ResourceAlreadyExistsException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
