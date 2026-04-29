package com.cartify.ecommerce.authorization.permission.application.response;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;


/**
 * Response record representing permission data in a serialized format.
 *
 * <p>This record is used to transfer permission information from the domain layer
 * to the application layer, providing a standardized representation of permission
 * data for API responses and other external communications.</p>
 *
 * <p>The response includes the module identifier, resource name (if applicable),
 * a bitmask representing combined permission bits, and the full path to the resource.</p>
 */
public record PermissionResponse(
        @NonNull String module,
        @Nullable String resource,
        int mask,
        @NonNull String fullPath
) {

    /**
     * Creates a PermissionResponse from a domain Permission entity.
     *
     * <p>This factory method transforms a domain-level Permission object into
     * a response record suitable for serialization and API responses.</p>
     *
     * @param permission the domain Permission object to convert
     * @return a new PermissionResponse instance populated with data from the permission
     * @throws NullPointerException if the input permission is null
     */
    public static @NonNull PermissionResponse from(@NonNull Permission permission) {
        return new PermissionResponse(
                permission.module(),
                permission.resource(),
                permission.mask(),
                permission.fullPath()
        );
    }
}
