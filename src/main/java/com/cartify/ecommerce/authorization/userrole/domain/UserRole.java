package com.cartify.ecommerce.authorization.userrole.domain;

import com.cartify.ecommerce.authorization.role.domain.RoleId;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a user role assignment in the system.
 * This aggregate root manages the relationship between users and their assigned roles,
 * including tracking who assigned the role and when it was revoked.
 */
@Getter
public class UserRole implements AggregateRoot<UserRole, UserRoleId> {

    /**
     * The unique identifier for this user role assignment.
     */
    private final UserRoleId id;

    /**
     * The identifier of the user to whom this role is assigned.
     */
    private final UserId userId;

    /**
     * The identifier of the role that is assigned to the user.
     */
    private final RoleId roleId;

    /**
     * The identifier of the user who assigned this role.
     */
    private final UserId assignedById;

    @Getter(AccessLevel.NONE)
    private final List<UserRoleEvent> events;

    /**
     * Creates a new user role assignment.
     *
     * @param id           The unique identifier for this assignment
     * @param userId       The identifier of the user to whom this role is assigned
     * @param roleId       The identifier of the role that is assigned to the user
     * @param assignedById The identifier of the user who assigned this role
     */
    private UserRole(
            UserRoleId id,
            UserId userId,
            RoleId roleId,
            UserId assignedById
    ) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.assignedById = assignedById;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new user role assignment.
     *
     * @param userId The identifier of the user to whom this role is assigned
     * @param roleId The identifier of the role that is assigned to the user
     * @param assignedById The identifier of the user who assigned this role
     * @return A new UserRole instance representing the assignment
     */
    public static @NonNull UserRole assign(
            @NonNull UserId userId,
            @NonNull RoleId roleId,
            @NonNull UserId assignedById
    ) {
        UserRole userRole = new UserRole(UserRoleId.generate(), userId, roleId, assignedById);
        userRole.events.add(new UserRoleEvent.Assigned(
                userRole.id,
                userRole.userId,
                userRole.roleId,
                userRole.assignedById
        ));

        return userRole;
    }

    /**
     * Reconstitutes a user role assignment from persisted data.
     *
     * @param id The unique identifier for this assignment
     * @param userId The identifier of the user to whom this role is assigned
     * @param roleId The identifier of the role that is assigned to the user
     * @param assignedById The identifier of the user who assigned this role
     * @return A UserRole instance representing the reconstituted assignment
     */
    public static @NonNull UserRole reconstitute(
            @NonNull UserRoleId id,
            @NonNull UserId userId,
            @NonNull RoleId roleId,
            @NonNull UserId assignedById
    ) {
        return new UserRole(id, userId, roleId, assignedById);
    }

    /**
     * Revokes this user role assignment.
     *
     * @param revokedById The identifier of the user who revoked this role
     */
    public void revoke(@NonNull UserId revokedById) {
        this.events.add(new UserRoleEvent.Revoked(
                this.id,
                this.userId,
                this.roleId,
                revokedById
        ));
    }

    /**
     * Retrieves and clears the events that have occurred for this user role assignment.
     *
     * @return A list of events that'd occurred since the last call to this method
     */
    public @NonNull List<UserRoleEvent> pullEvents() {
        List<UserRoleEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Retrieves the events that have occurred for this user role assignment.
     *
     * @return A list of events that'd occurred since the last call to pullEvents()
     */
    public @NonNull List<UserRoleEvent> getEvents() {
        return List.copyOf(this.events);
    }
}
