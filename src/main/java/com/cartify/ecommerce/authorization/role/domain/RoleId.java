package com.cartify.ecommerce.authorization.role.domain;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Value object representing a unique identifier for a {@link Role}.
 *
 * @param id the underlying UUID must not be null
 */
public record RoleId(@NonNull UUID id) implements Identifier {

    /**
     * Creates a new {@code RoleId} with a randomly generated UUID.
     */
    public static @NonNull RoleId generate() {
        return new RoleId(UUID.randomUUID());
    }

    /**
     * Creates a {@code RoleId} from an existing {@link UUID}.
     *
     * @param id the UUID to wrap
     */
    public static @NonNull RoleId of(@NonNull UUID id) {
        return new RoleId(id);
    }

    /**
     * Creates a {@code RoleId} from a UUID string.
     *
     * @param id the UUID string to parse
     * @throws IllegalArgumentException if the string is not a valid UUID
     */
    public static @NonNull RoleId of(@NonNull String id) {
        return new RoleId(UUID.fromString(id));
    }
}
