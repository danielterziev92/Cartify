package com.cartify.ecommerce.authorization.role.domain;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import com.cartify.ecommerce.shared.domain.exception.BusinessRuleException;
import com.cartify.ecommerce.shared.domain.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.jmolecules.ddd.types.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Aggregate root representing a role in the authorization model.
 *
 * <p>A role groups a set of {@link Permission}s and can be assigned to users to grant
 * access control. Roles may be user-defined or system-managed; system roles are
 * immutable and cannot have their permissions modified or be deleted.
 *
 * <p>All mutating operations raise domain events accessible via {@link #pullEvents()}.
 */
@Getter
public class Role implements AggregateRoot<Role, RoleId> {

    private final RoleId id;
    private String name;
    /**
     * {@code true} if this role is managed by the system and cannot be mutated.
     */
    private final boolean system;
    private final Set<Permission> permissions;

    @Getter(AccessLevel.NONE)
    private final List<RoleEvent> events;

    private Role(
            RoleId id,
            String name,
            boolean system,
            Set<Permission> permissions
    ) {
        this.id = id;
        this.name = name;
        this.system = system;
        this.permissions = permissions;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new user-defined role and raises a {@link RoleEvent.Created} event.
     *
     * @param name        display name; trimmed, lowercased, and validated against {@link RoleRule.Name}
     * @param permissions initial set of permissions to assign
     * @return the newly created role
     * @throws com.cartify.ecommerce.shared.domain.exception.InvalidValueException if {@code name} is blank, too long, or does not match the allowed pattern
     */
    public static @NonNull Role create(@NonNull String name, @NonNull Set<Permission> permissions) {
        String normalizedName = validateAndNormalizeName(name);
        Role role = new Role(RoleId.generate(), normalizedName, false, permissions);
        role.events.add(new RoleEvent.Created(role.id, normalizedName, permissions));

        return role;
    }

    /**
     * Reconstitutes a role from persistent storage without raising any domain events.
     *
     * @param id          persisted role identity
     * @param name        persisted name (assumed already validated)
     * @param permissions persisted permission set
     * @return the reconstituted role
     */
    public static @NonNull Role reconstitute(
            @NonNull RoleId id,
            @NonNull String name,
            @NonNull Set<Permission> permissions
    ) {
        return new Role(id, name, false, permissions);
    }

    /**
     * Renames this role and raises a {@link RoleEvent.NameChanged} event.
     * No-ops if the normalized name is identical to the current name.
     *
     * @param newName the new display name; subject to the same validation as {@link #create}
     */
    public void changeName(@NonNull String newName) {
        this.guardNoSystem();
        String normalizedName = validateAndNormalizeName(newName);

        if (this.name.equals(normalizedName)) return;

        String oldName = this.name;
        this.name = normalizedName;
        this.events.add(new RoleEvent.NameChanged(this.id, oldName, this.name));
    }

    /**
     * Replaces the full permission set of this role and raises a {@link RoleEvent.PermissionsUpdated} event.
     *
     * @param permissions the new set of permissions; replaces all existing ones
     * @throws com.cartify.ecommerce.shared.domain.exception.BusinessRuleException if this is a system role
     */
    public void replacePermissions(@NonNull Set<Permission> permissions) {
        this.guardNoSystem();

        this.permissions.clear();
        this.permissions.addAll(permissions);
        this.events.add(new RoleEvent.PermissionsUpdated(this.id, permissions));
    }

    /**
     * Adds a single permission to this role and raises a {@link RoleEvent.PermissionsUpdated} event.
     * No-ops if the permission is already present.
     *
     * @param permission the permission to add
     * @throws com.cartify.ecommerce.shared.domain.exception.BusinessRuleException if this is a system role
     */
    public void addPermission(@NonNull Permission permission) {
        this.guardNoSystem();

        boolean changed = this.permissions.add(permission);
        if (!changed) return;
        this.events.add(new RoleEvent.PermissionsUpdated(this.id, Set.of(permission)));
    }

    /**
     * Removes a single permission from this role and raises a {@link RoleEvent.PermissionsUpdated} event.
     * No-ops if the permission is not present.
     *
     * @param permission the permission to remove
     * @throws com.cartify.ecommerce.shared.domain.exception.BusinessRuleException if this is a system role
     */
    public void removePermission(@NonNull Permission permission) {
        this.guardNoSystem();

        boolean changed = this.permissions.remove(permission);
        if (!changed) return;
        this.events.add(new RoleEvent.PermissionsUpdated(this.id, Set.of()));
    }

    /**
     * Marks this role for deletion by raising a {@link RoleEvent.Deleted} event.
     *
     * @throws com.cartify.ecommerce.shared.domain.exception.BusinessRuleException if this is a system role
     */
    public void delete() {
        this.guardNoSystem();
        this.events.add(new RoleEvent.Deleted(this.id));
    }

    /**
     * Returns all pending domain events and clears the internal event list.
     * Intended to be called by the repository or application service after persisting the aggregate.
     *
     * @return an immutable snapshot of the events raised since the last pull
     */
    public @NonNull List<RoleEvent> pullEvents() {
        List<RoleEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of pending domain events without clearing them.
     *
     * @return an immutable copy of the current event list
     */
    public @NonNull List<RoleEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Returns an immutable copy of the permissions currently assigned to this role.
     *
     * @return unmodifiable set of permissions
     */
    public @NonNull Set<Permission> getPermissions() {
        return Set.copyOf(this.permissions);
    }

    /**
     * @throws com.cartify.ecommerce.shared.domain.exception.BusinessRuleException if this is a system role
     */
    private void guardNoSystem() {
        if (this.system) throw new BusinessRuleException(RoleRule.System.IMMUTABLE_MSG);
    }

    /**
     * Trims, lowercases, and validates a role name against {@link RoleRule.Name}.
     *
     * @param name raw input name
     * @return normalized (trimmed and lowercased) name
     * @throws com.cartify.ecommerce.shared.domain.exception.InvalidValueException if blank, exceeds max length, or fails the pattern check
     */
    private static @NonNull String validateAndNormalizeName(@NonNull String name) {
        String trimmedName = name.trim();

        if (trimmedName.isBlank()) throw new InvalidValueException(RoleRule.Name.BLANK_MSG);
        if (trimmedName.length() > RoleRule.Name.MAX_LENGTH)
            throw new InvalidValueException(RoleRule.Name.MAX_LENGTH_MSG, trimmedName.length(), RoleRule.Name.MAX_LENGTH);

        if (!RoleRule.Name.PATTERN.matcher(trimmedName).matches())
            throw new InvalidValueException(RoleRule.Name.INVALID_MSG, trimmedName);

        return trimmedName.toLowerCase();
    }
}
