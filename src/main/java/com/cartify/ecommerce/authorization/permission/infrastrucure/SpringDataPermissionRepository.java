package com.cartify.ecommerce.authorization.permission.infrastrucure;

import io.lettuce.core.dynamic.annotation.Param;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing permission entities in the authorization system.
 * Provides data access operations for permission records stored in the database.
 */
public interface SpringDataPermissionRepository extends CrudRepository<PermissionEntity, UUID> {

    @NonNull List<PermissionEntity> findAll();

    @NonNull List<PermissionEntity> findAllByModuleId(@NonNull UUID moduleId);

    @NonNull List<PermissionEntity> findAllByModule(@NonNull String module);

    @Query("""
            SELECT id, module, resource, mask FROM auth.permissions
                    WHERE module = :module
                    AND resource IS NOT DISTINCT FROM :resource
            """)
    Optional<PermissionEntity> findByModuleAndResource(
            @Param("module") @NonNull String module,
            @Param("resource") @Nullable String resource);


    @Query("""
            SELECT 1 FROM auth.permissions
                    WHERE module = :module
                    AND resource IS NOT DISTINCT FROM :resource
                    LIMIT 1
            """)
    boolean existsByModuleAndResource(
            @Param("module") @NonNull String module,
            @Param("resource") @Nullable String resource);
}
