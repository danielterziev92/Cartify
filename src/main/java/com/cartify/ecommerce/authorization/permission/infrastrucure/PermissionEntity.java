package com.cartify.ecommerce.authorization.permission.infrastrucure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Entity representing a permission in the authorization system.
 *
 * <p>This entity maps to the 'permissions' table in the 'auth' schema and stores
 * information about individual permissions that can be granted to users or roles.
 * Each permission consists of a module identifier, resource identifier, and a mask
 * that represents the specific operations allowed on that resource.</p>
 */
@Data
@Table(name = "permissions", schema = "auth")
public class PermissionEntity {

    @Id
    @Column("id")
    private UUID id;

    /**
     * The module to which this permission belongs.
     *
     * <p>This field identifies the system module or domain to which the permission applies.
     * For example, "users", "products", "orders", etc.</p>
     */
    @Column("module")
    private String module;

    /**
     * The resource to which this permission applies.
     *
     * <p>This field identifies the specific resource or entity within a module
     * to which this permission grants access. For example, "user", "product", "order", etc.</p>
     */
    @Column("resource")
    private String resource;

    /**
     * The permission mask representing the operations allowed on the resource.
     *
     * <p>This integer field stores a bitwise mask of permissions that define
     * what operations (read, create, update, delete) are allowed on the resource.
     * The mask is constructed using values from {@link com.cartify.ecommerce.authorization.permission.domain.PermissionBit}.</p>
     */
    @Column("mask")
    private int mask;
}
