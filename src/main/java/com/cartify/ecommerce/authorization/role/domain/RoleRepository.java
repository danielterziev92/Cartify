package com.cartify.ecommerce.authorization.role.domain;

import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Role} aggregates.
 *
 * <p>Provides persistence operations for roles, including
 * lookup by ID and name, as well as bulk retrieval and deletion.
 */
public interface RoleRepository extends Repository<Role, RoleId> {

    /**
     * Returns all existing roles.
     */
    List<Role> findAll();

    /**
     * Returns the role with the given ID if it exists.
     */
    Optional<Role> findById(@NonNull RoleId id);

    /**
     * Returns the role with the given name if one exists.
     */
    Optional<Role> findByName(@NonNull String name);

    /**
     * Returns {@code true} if a role with the given name already exists.
     */
    boolean existsByName(@NonNull String name);

    /**
     * Persists the given role aggregate.
     */
    void save(@NonNull Role role);

    /**
     * Deletes the role with the given ID.
     */
    void deleteById(@NonNull RoleId id);
}
