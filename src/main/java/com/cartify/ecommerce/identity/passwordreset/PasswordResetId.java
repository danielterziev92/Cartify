package com.cartify.ecommerce.identity.passwordreset;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Strongly typed identifier for the {@link PasswordReset} aggregate.
 */
public record PasswordResetId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new random {@code PasswordResetId}.
     */
    public static @NonNull PasswordResetId generate() {
        return new PasswordResetId(UUID.randomUUID());
    }

    /**
     * Wraps an existing {@link UUID} as a {@code PasswordResetId}.
     */
    public static @NonNull PasswordResetId of(@NonNull UUID value) {
        return new PasswordResetId(value);
    }

    /**
     * Parses a UUID string into a {@code PasswordResetId}.
     *
     * @throws IllegalArgumentException if {@code value} is not a valid UUID
     */
    public static @NonNull PasswordResetId of(@NonNull String value) {
        return new PasswordResetId(UUID.fromString(value));
    }
}
