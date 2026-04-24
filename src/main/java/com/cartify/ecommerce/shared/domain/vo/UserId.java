package com.cartify.ecommerce.shared.domain.vo;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Value object representing a unique identifier for a user.
 *
 * @param value the underlying UUID must not be null
 */
public record UserId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@code UserId} with a randomly generated UUID.
     */
    public static @NonNull UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    /**
     * Creates a {@code UserId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap
     */
    public static @NonNull UserId of(UUID value) {
        return new UserId(value);
    }

    /**
     * Creates a {@code UserId} from a UUID string.
     *
     * @param value the UUID string to parse
     * @throws IllegalArgumentException if the string is not a valid UUID
     */
    public static @NonNull UserId of(String value) {
        return new UserId(UUID.fromString(value));
    }
}
