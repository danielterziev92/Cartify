package com.cartify.ecommerce.authorization.role.domain;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.util.Set;

/**
 * Sealed interface for domain events published by the {@link Role} aggregate.
 *
 * <p>Every event carries the ID of the role it concerns and the timestamp at
 * which it occurred. Permitted subtypes cover the full lifecycle of a role:
 * creation, name changes, permission changes, and deletion.
 */
public sealed interface RoleEvent extends DomainEvent permits
        RoleEvent.Created,
        RoleEvent.NameChanged,
        RoleEvent.PermissionsUpdated,
        RoleEvent.Deleted {

    /**
     * Returns the ID of the role this event concerns.
     */
    @NonNull RoleId id();

    /**
     * Returns the instant at which this event occurred.
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new role is created with its initial set of permissions.
     */
    record Created(
            @NonNull RoleId id,
            @NonNull String name,
            @NonNull Set<Permission> permissions,
            @NonNull Instant occurredAt
    ) implements RoleEvent {
        public Created(@NonNull RoleId id, @NonNull String name, @NonNull Set<Permission> permissions) {
            this(id, name, permissions, Instant.now());
        }
    }

    /**
     * Published when the name of an existing role is changed.
     */
    record NameChanged(
            @NonNull RoleId id,
            @NonNull String oldName,
            @NonNull String newName,
            @NonNull Instant occurredAt
    ) implements RoleEvent {
        public NameChanged(@NonNull RoleId id, @NonNull String oldName, @NonNull String newName) {
            this(id, oldName, newName, Instant.now());
        }
    }

    /**
     * Published when the permission set of an existing role is replaced.
     */
    record PermissionsUpdated(
            @NonNull RoleId id,
            @NonNull Set<Permission> permissions,
            @NonNull Instant occurredAt
    ) implements RoleEvent {
        public PermissionsUpdated(@NonNull RoleId id, @NonNull Set<Permission> permissions) {
            this(id, permissions, Instant.now());
        }
    }

    /**
     * Published when a role is permanently deleted.
     */
    record Deleted(@NonNull RoleId id, @NonNull Instant occurredAt) implements RoleEvent {
        public Deleted(@NonNull RoleId id) {
            this(id, Instant.now());
        }
    }
}
