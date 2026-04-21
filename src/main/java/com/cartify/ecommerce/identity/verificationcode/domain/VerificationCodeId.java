package com.cartify.ecommerce.identity.verificationcode.domain;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Strongly typed identifier for a {@link VerificationCode} aggregate.
 *
 * <p>Wraps a {@link UUID} to prevent accidental misuse of raw identifiers across
 * aggregate boundaries.
 */
public record VerificationCodeId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@code VerificationCodeId} backed by a randomly generated UUID.
     */
    public static @NonNull VerificationCodeId generate() {
        return new VerificationCodeId(UUID.randomUUID());
    }

    /**
     * Creates a {@code VerificationCodeId} from an existing {@link UUID}.
     */
    public static @NonNull VerificationCodeId of(@NonNull UUID value) {
        return new VerificationCodeId(value);
    }

    /**
     * Creates a {@code VerificationCodeId} by parsing a UUID string.
     *
     * @throws IllegalArgumentException if {@code value} is not a valid UUID
     */
    public static @NonNull VerificationCodeId of(@NonNull String value) {
        return new VerificationCodeId(UUID.fromString(value));
    }
}
