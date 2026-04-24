package com.cartify.ecommerce.shared.domain.exception;

/**
 * Thrown when an operation violates a business rule (e.g., a state transition that is not allowed).
 */
public class BusinessRuleException extends DomainException {

    /**
     * Creates a new {@code BusinessRuleException}.
     *
     * @param messageCode the i18n message key identifying the violated rule
     * @param args        optional arguments interpolated into the resolved message
     */
    public BusinessRuleException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
