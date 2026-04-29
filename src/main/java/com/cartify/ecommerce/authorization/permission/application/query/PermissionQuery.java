package com.cartify.ecommerce.authorization.permission.application.query;

import org.jspecify.annotations.NonNull;


/**
 * Sealed query hierarchy for permission retrieval.
 *
 * <p>Handled by {@link com.cartify.ecommerce.authorization.permission.application.query.PermissionsQueryHandler}. Pattern matching on the permitted
 * subtypes guarantees exhaustiveness — adding a new subtype forces all handlers
 * to be updated at compile time.
 *
 * <p>Usage:
 * <pre>
 *     handler.handle(new PermissionQuery.All());
 *     handler.handle(new PermissionQuery.ByModule("catalog"));
 * </pre>
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
 