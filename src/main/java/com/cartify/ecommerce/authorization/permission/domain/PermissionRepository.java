package com.cartify.ecommerce.authorization.permission.domain;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Repository interface for managing permission entities in the system.
 * Provides methods to query and persist permission data.
 *
 * <p>This repository is responsible for managing the lifecycle of permission entities,
 * including their creation, retrieval, and persistence to the underlying data store.
 * It supports various query operations for permissions based on module, resource, and other criteria.</p>
 */
public interface PermissionRepository {

    /**
     * Retrieves all permissions from the system.
     *
     * @return A list of all permission entities in the system
     */
    @NonNull List<Permission> findAll();

    /**
     * Retrieves all permissions associated with a specific module.
     *
     * @param module The identifier of the module to filter permissions by
     * @return A list of permission entities associated with the specified module
     */
    @NonNull List<Permission> findAllByModule(@NonNull String module);

    /**
     * Retrieves all permissions associated with a specific module.
     *
     * @param moduleId The identifier of the module to filter permissions by
     * @return A list of permission entities associated with the specified module
     */
    @NonNull List<Permission> findAllByModuleId(@NonNull UUID moduleId);

    /**
     * Finds a permission by its module and resource identifiers.
     *
     * @param module   The module identifier for the permission
     * @param resource The resource identifier for the permission
     * @return An Optional containing the matching Permission if found, or empty if not found
     */
    Optional<Permission> findByModuleAndResource(@NonNull String module, @NonNull String resource);

    /**
     * Checks if a permission exists with the specified module and resource identifiers.
     *
     * @param module   The module identifier for the permission
     * @param resource The resource identifier for the permission
     * @return true if a permission with the specified module and resource exists, false otherwise
     */
    boolean existsByModuleAndResource(@NonNull String module, @NonNull String resource);

    /**
     * Saves a permission entity to the system.
     *
     * @param permission The permission to save
     */
    void save(@NonNull Permission permission);

    /**
     * Saves multiple permission entities to the system in a single operation.
     *
     * @param toCreate A list of permission entities to create
     */
    void saveAll(List<Permission> toCreate);

    /**
     * Deletes multiple permission entities from the system in a single operation.
     *
     * @param toDelete A list of permission entities to delete
     */
    void deleteAll(List<Permission> toDelete);
}
