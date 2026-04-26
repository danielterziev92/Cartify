package com.cartify.ecommerce.authorization.role.domain;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.util.Set;

/**
 * Sealed interface for domain events published by the {@link Role} aggregate.
 *
 * <p>This sealed interface defines the complete lifecycle of role-related events in the
 * authorization system. Each event carries the ID of the role it concerns and the timestamp at
 * which it occurred. The sealed hierarchy ensures type safety while allowing for extensibility
 * through the permitted subtypes.</p>
 *
 * <p>Permitted subtypes cover the full lifecycle of a role:</p>
 * <ul>
 *   <li>{@link Created} - Published when a new role is created with its initial set of permissions</li>
 *   <li>{@link NameChanged} - Published when the name of an existing role is changed</li>
 *   <li>{@link PermissionsUpdated} - Published when the permission set of an existing role is replaced</li>
 *   <li>{@link Deleted} - Published when a role is permanently deleted</li>
 * </ul>
 *
 * <p>Event handlers should subscribe to these events to maintain consistency across the system,
 * such as updating role caches, notifying dependent services, or auditing changes.</p>
 */
public sealed interface RoleEvent extends DomainEvent permits
        RoleEvent.Created,
        RoleEvent.NameChanged,
        RoleEvent.PermissionsUpdated,
        RoleEvent.Deleted {

    /**
     * Returns the ID of the role this event concerns.
     *
     * @return the non-null role identifier
     */
    @NonNull RoleId id();

    /**
     * Returns the instant at which this event occurred.
     *
     * @return the non-null timestamp of when the event occurred
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new role is created with its initial set of permissions.
     *
     * <p>This event represents the creation of a new role in the system. The event contains
     * the role's identifier, name, and its initial set of permissions. This event should be
     * used to initialize any role-related caches or notify other services about the new role.</p>
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
     *
     * <p>This event represents a modification to an existing role's name. The event contains
     * the role's identifier, the old name, and the new name. This event should be used to
     * update any cached role names or notify dependent services about the name change.</p>
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
     *
     * <p>This event represents a complete replacement of a role's permissions. The event contains
     * the role's identifier and the new set of permissions. This event should be used to update
     * any permission caches or notify services that depend on role permissions.</p>
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
     *
     * <p>This event represents the permanent removal of a role from the system. The event contains
     * only the role's identifier and timestamp. This event should be used to clean up any
     * references to the role or notify dependent services about the deletion.</p>
     */
    record Deleted(@NonNull RoleId id, @NonNull Instant occurredAt) implements RoleEvent {
        public Deleted(@NonNull RoleId id) {
            this(id, Instant.now());
        }
    }
}
