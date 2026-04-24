package com.cartify.ecommerce.shared.domain.exception;

import lombok.Getter;

/**
 * Base class for all domain-layer exceptions in the application.
 *
 * <p>Subclasses should be thrown when a domain rule or invariant is violated.
 * The {@code messageCode} is intended to be resolved to a localized message
 * at the application boundary (e.g., via {@code MessageSource}).
 */
@Getter
public class DomainException extends RuntimeException {
    /**
     * The message code used for i18n resolution.
     */
    private final String messageCode;

    /**
     * Optional arguments interpolated into the resolved message.
     */
    private final Object[] args;

    /**
     * Creates a new {@code DomainException}.
     *
     * @param messageCode the i18n message key identifying the error
     * @param args        optional arguments passed to the message resolver
     */
    protected DomainException(String messageCode, Object... args) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = args;
    }
}
