package com.cartify.ecommerce.authorization.role.domain;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Value object representing a unique identifier for a {@link Role}.
 *
 * <p>
 * This immutable value object wraps a UUID to provide type-safe identification
 * for role entities within the authorization system. It ensures that role identifiers
 * are always valid and properly formatted.
 * </p>
 *
 * @param id the underlying UUID must not be null
 */
public record RoleId(@NonNull UUID id) implements Identifier {

    /**
     * Generates a new random UUID and returns a new RoleId instance.
     *
     * @return a new RoleId with a randomly generated UUID
     */
    public static @NonNull RoleId generate() {
        return new RoleId(UUID.randomUUID());
    }

    /**
     * Creates a new RoleId instance from the provided UUID.
     *
     * @param id the UUID to wrap in a RoleId
     * @return a new RoleId instance with the provided UUID
     */
    public static @NonNull RoleId of(@NonNull UUID id) {
        return new RoleId(id);
    }

    /**
     * Creates a new RoleId instance from the provided UUID string representation.
     *
     * @param id the UUID string to parse and wrap in a RoleId
     * @return a new RoleId instance with the parsed UUID
     * @throws IllegalArgumentException if the string is not a valid UUID
     */
    public static @NonNull RoleId of(@NonNull String id) {
        return new RoleId(UUID.fromString(id));
    }
}
