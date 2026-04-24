package com.cartify.ecommerce.shared.domain.vo;

import com.cartify.ecommerce.shared.domain.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

import java.util.regex.Pattern;

/**
 * Value object representing a validated, normalized email address.
 *
 * <p>On construction the value is trimmed and lowercased, then validated against
 * RFC 5321 length constraints and an RFC 5322-based format pattern.
 *
 * @param value the normalized email address string must not be null
 */
public record Email(@NonNull String value) implements ValueObject {
    /**
     * i18n key used when the email value is blank.
     */
    public static final String BLANK_MSG = "user.email.blank";
    /**
     * i18n key used when the email value exceeds {@link #MAX_LENGTH}.
     */
    public static final String LENGTH_MSG = "user.email.length";
    /**
     * i18n key used when the email value does not match {@link #PATTERN}.
     */
    public static final String INVALID_MSG = "user.email.invalid";

    /**
     * Maximum allowed length of an email address in characters.
     * Defined by RFC 5321.
     */
    public static final int MAX_LENGTH = 254;

    /**
     * Regular expression used to validate the format of an email address.
     * Based on RFC 5322, allowing standard local parts and domain labels
     * with a top-level domain between 2 and 63 characters.
     */
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$");

    /**
     * Compact constructor that normalizes and validates the email address.
     *
     * @throws InvalidValueException if the value is blank, exceeds {@link #MAX_LENGTH}, or fails format validation
     */
    public Email {
        value = value.trim().toLowerCase();

        if (value.isBlank()) throw new InvalidValueException(BLANK_MSG);
        if (value.length() > MAX_LENGTH) throw new InvalidValueException(LENGTH_MSG, value.length(), MAX_LENGTH);
        if (!PATTERN.matcher(value).matches()) throw new InvalidValueException(INVALID_MSG, value);
    }

    /**
     * Factory method for creating an {@code Email} from a raw string.
     *
     * @param value the raw email string to normalize and validate
     * @throws InvalidValueException if validation fails
     */
    public static @NonNull Email of(@NonNull String value) {
        return new Email(value);
    }
}
