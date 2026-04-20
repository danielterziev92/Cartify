package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.shared.domain.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a BCrypt-hashed password.
 *
 * <p>Accepts only pre-hashed values — the application must hash raw plaintext passwords
 * before being wrapped in this type. Validation enforces that the value
 * is non-blank, exactly 60 characters long, and matches the BCrypt {@code $2a$}/$2b$}
 * format defined in {@link UserRule.Password}.
 *
 * @param value the BCrypt hash string must not be null
 */
public record PasswordHash(@NonNull String value) implements ValueObject {

    /**
     * Compact constructor that validates the BCrypt hash format.
     *
     * @throws InvalidValueException if the value is blank, not exactly {@link UserRule.Password#LENGTH} characters,
     *                               or does not match the BCrypt {@link UserRule.Password#PATTERN}
     */
    public PasswordHash {
        if (value.isBlank()) throw new InvalidValueException(UserRule.Password.BLANK_MSG);
        if (value.length() != UserRule.Password.LENGTH) throw new InvalidValueException(UserRule.Password.LENGTH_MSG);
        if (!UserRule.Password.PATTERN.matcher(value).matches())
            throw new InvalidValueException(UserRule.Password.INVALID_MSG);
    }

    /**
     * Factory method for wrapping an existing BCrypt hash string.
     *
     * @param value the BCrypt hash to wrap
     * @throws InvalidValueException if validation fails
     */
    public static @NonNull PasswordHash of(@NonNull String value) {
        return new PasswordHash(value);
    }
}
