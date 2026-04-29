package com.cartify.ecommerce.authorization.permission.infrastrucure;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import com.cartify.ecommerce.authorization.permission.domain.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JDBC-based repository implementation for managing permission entities in the database.
 * This implementation provides CRUD operations for permissions using Spring Data JDBC and
 * direct SQL execution for batch operations.
 */
@Repository
@RequiredArgsConstructor
public class PermissionJdbcRepository implements PermissionRepository {

    /**
     * The Spring Data repository for permission entities.
     */
    private final SpringDataPermissionRepository springRepository;

    /**
     * The mapper for converting between domain objects and entities.
     */
    private final PermissionMapper mapper;

    /**
     * The named parameter JDBC template for executing batch operations.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Retrieves all permission entities from the database.
     *
     * @return a list of all Permission domain objects
     */
    @Override
    public @NonNull List<Permission> findAll() {
        return this.springRepository.findAll()
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    /**
     * Retrieves all permission entities for a specific module from the database.
     *
     * @param module the module name to filter by
     * @return a list of Permission domain objects for the specified module
     */
    @Override
    public @NonNull List<Permission> findAllByModule(@NonNull String module) {
        return this.springRepository.findAllByModule(module)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    /**
     * Retrieves all permission entities for a specific module ID from the database.
     *
     * @param moduleId the module ID to filter by
     * @return a list of Permission domain objects for the specified module ID
     */
    @Override
    public @NonNull List<Permission> findAllByModuleId(@NonNull UUID moduleId) {
        return this.springRepository.findAllByModuleId(moduleId)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    /**
     * Finds a permission by its module and resource.
     *
     * @param module   the module name to search for
     * @param resource the resource name to search for
     * @return an Optional containing the Permission domain object if found, or empty if not found
     */
    @Override
    public Optional<Permission> findByModuleAndResource(@NonNull String module, @NonNull String resource) {
        return this.springRepository.findByModuleAndResource(module, resource)
                .map(this.mapper::toDomain);
    }

    /**
     * Checks if a permission exists for the specified module and resource.
     *
     * @param module   the module name to check
     * @param resource the resource name to check
     * @return true if a permission exists for the specified module and resource, false otherwise
     */
    @Override
    public boolean existsByModuleAndResource(@NonNull String module, @NonNull String resource) {
        return this.springRepository.existsByModuleAndResource(module, resource);
    }

    /**
     * Saves a permission to the database.
     *
     * @param permission permission domain object to save
     */
    @Override
    public void save(@NonNull Permission permission) {
        this.springRepository.save(this.mapper.toEntity(permission));
    }

    /**
     * Saves multiple permissions to the database in a batch operation.
     *
     * @param toCreate the list of Permission domain objects to create
     */
    @Override
    public void saveAll(@NonNull List<Permission> toCreate) {
        if (toCreate.isEmpty()) return;

        MapSqlParameterSource[] params = toCreate.stream()
                .map(permission -> new MapSqlParameterSource()
                        .addValue("module", permission.module())
                        .addValue("resource", permission.resource())
                        .addValue("mask", permission.mask()))
                .toArray(MapSqlParameterSource[]::new);

        this.jdbcTemplate.batchUpdate(
                """
                        INSERT INTO auth.permissions (id, module, resource, mask)
                                VALUES (gen_random_uuid(), :module, :resource, :mask)
                                ON CONFLICT (module, resource) DO NOTHING
                        """, params);
    }

    /**
     * Deletes multiple permissions from the database in a batch operation.
     *
     * @param toDelete the list of Permission domain objects to delete
     */
    @Override
    public void deleteAll(@NonNull List<Permission> toDelete) {
        if (toDelete.isEmpty()) return;

        MapSqlParameterSource[] params = toDelete.stream()
                .map(permission -> new MapSqlParameterSource()
                        .addValue("module", permission.module())
                        .addValue("resource", permission.resource()))
                .toArray(MapSqlParameterSource[]::new);

        this.jdbcTemplate.batchUpdate(
                """
                        DELETE FROM auth.permissions
                        WHERE module = :module
                        AND resource IS NOT DISTINCT FROM  :resource
                        """, params);
    }
}
