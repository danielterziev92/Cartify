package com.cartify.ecommerce.authorization.permission.application.query;

import org.jspecify.annotations.NonNull;


/**
 * Query types for retrieving permission information.
 */
public sealed interface PermissionQuery permits
        PermissionQuery.All,
        PermissionQuery.ByModule {

    /**
     * Query to retrieve all permissions.
     */
    record All() implements PermissionQuery {
    }

    /**
     * Query to retrieve permissions by module.
     *
     * @param module the module name to filter by
     */
    record ByModule(@NonNull String module) implements PermissionQuery {
    }
}
 