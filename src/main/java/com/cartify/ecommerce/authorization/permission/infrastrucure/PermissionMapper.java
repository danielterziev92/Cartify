package com.cartify.ecommerce.authorization.permission.infrastrucure;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper interface for converting between Permission domain objects and PermissionEntity data transfer objects.
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {

    /**
     * Converts a PermissionEntity to a Permission domain object.
     *
     * @param permissionEntity the entity to convert
     * @return the converted domain object
     */
    Permission toDomain(@NonNull PermissionEntity permissionEntity);

    /**
     * Converts a Permission domain object to a PermissionEntity.
     *
     * @param permission the domain object to convert
     * @return the converted entity
     */
    @Mapping(target = "id", ignore = true)
    PermissionEntity toEntity(@NonNull Permission permission);
}
