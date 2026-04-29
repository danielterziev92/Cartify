package com.cartify.ecommerce.authorization.permission.domain;

import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;


/**
 * Domain event representing permission-related occurrences.
 *
 * <p>This sealed interface defines events that occur in relation to permissions,
 * such as when a permission is removed.</p>
 */
public sealed interface PermissionEvent extends DomainEvent permits
        PermissionEvent.Removed {

    /**
     * Domain event indicating that a permission has been removed.
     *
     * @param module   the module name where the permission was located
     * @param resource the specific resource within the module, or null if not applicable
     */
    record Removed(@NonNull String module, @Nullable String resource) implements PermissionEvent {
    }
}
