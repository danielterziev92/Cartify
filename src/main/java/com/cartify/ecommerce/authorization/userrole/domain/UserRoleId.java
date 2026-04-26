package com.cartify.ecommerce.authorization.userrole.domain;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Typed identifier for the {@link UserRole} aggregate root.
 * This immutable value object represents a unique identifier for user role entities
 * and ensures type safety when working with user role IDs throughout the application.
 */
public record UserRoleId(@NonNull UUID value) implements Identifier {

    /**
     * Generates a new random UUID and returns a new UserRoleId instance.
     * 
     * @return a new UserRoleId with a randomly generated UUID
     */
    public static @NonNull UserRoleId generate() {
        return new UserRoleId(UUID.randomUUID());
    }

    /**
     * Creates a new UserRoleId instance from the provided UUID.
     * 
     * @param value the UUID to wrap in a UserRoleId
     * @return a new UserRoleId instance with the provided UUID
     */
    public static @NonNull UserRoleId of(@NonNull UUID value) {
        return new UserRoleId(value);
    }

    /**
     * Creates a new UserRoleId instance from the provided UUID string representation.
     * 
     * @param value the UUID string to parse and wrap in a UserRoleId
     * @return a new UserRoleId instance with the parsed UUID
     */
    public static @NonNull UserRoleId of(@NonNull String value) {
        return new UserRoleId(UUID.fromString(value));
    }
}
