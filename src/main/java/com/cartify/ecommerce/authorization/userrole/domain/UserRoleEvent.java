package com.cartify.ecommerce.authorization.userrole.domain;

import com.cartify.ecommerce.authorization.role.domain.RoleId;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;


/**
 * Domain event representing user role assignment and revocation operations.
 * This sealed interface defines the contract for events related to user-role assignments.
 */
public sealed interface UserRoleEvent extends DomainEvent permits
        UserRoleEvent.Assigned,
        UserRoleEvent.Revoked {

    /**
     * Returns the unique identifier of this user role event.
     *
     * @return the user role event ID
     */
    @NonNull UserRoleId id();

    /**
     * Returns the timestamp when this event occurred.
     *
     * @return the occurrence timestamp
     */
    @NonNull Instant occurredAt();

    /**
     * Event representing a user role assignment operation.
     * This record is published when a role is assigned to a user.
     */
    record Assigned(
            @NonNull UserRoleId id,
            @NonNull UserId userId,
            @NonNull RoleId roleId,
            @NonNull UserId assignedById,
            @NonNull Instant occurredAt
    ) implements UserRoleEvent {
        /**
         * Creates a new assigned event with the current timestamp.
         *
         * @param id           the unique identifier of this event
         * @param userId       the ID of the user to whom the role was assigned
         * @param roleId       the ID of the role that was assigned
         * @param assignedById the ID of the user who performed the assignment
         */
        public Assigned(
                @NonNull UserRoleId id,
                @NonNull UserId userId,
                @NonNull RoleId roleId,
                @NonNull UserId assignedById) {
            this(id, userId, roleId, assignedById, Instant.now());
        }
    }

    /**
     * Event representing a user role revocation operation.
     * This record is published when a role is revoked from a user.
     */
    record Revoked(
            @NonNull UserRoleId id,
            @NonNull UserId userId,
            @NonNull RoleId roleId,
            @NonNull UserId revokedById,
            @NonNull Instant occurredAt
    ) implements UserRoleEvent {
        /**
         * Creates a new revoked event with the current timestamp.
         *
         * @param id          the unique identifier of this event
         * @param userId      the ID of the user from whom the role was revoked
         * @param roleId      the ID of the role that was revoked
         * @param revokedById the ID of the user who performed the revocation
         */
        public Revoked(
                @NonNull UserRoleId id,
                @NonNull UserId userId,
                @NonNull RoleId roleId,
                @NonNull UserId revokedById) {
            this(id, userId, roleId, revokedById, Instant.now());
        }
    }
}
