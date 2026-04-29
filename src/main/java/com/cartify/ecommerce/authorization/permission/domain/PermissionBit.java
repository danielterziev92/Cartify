package com.cartify.ecommerce.authorization.permission.domain;

import org.springframework.modulith.NamedInterface;

/**
 * Bit flags representing individual operations that can be granted on a resource.
 *
 * <p>Permissions are composed via bitwise OR, allowing multiple operations
 * to be expressed as a single integer mask. Use {@link #ALL} to grant full access.
 *
 * <p>Example:
 * <pre>
 *   int mask = PermissionBit.READ.value | PermissionBit.CREATE.value; // 3
 *   boolean canRead = (mask & PermissionBit.READ.value) != 0; // true
 * </pre>
 *
 * <p>Each permission bit corresponds to a specific operation that can be performed
 * on a resource. These bits can be combined using bitwise OR operations to create
 * permission masks that represent complex access control policies.</p>
 */
@NamedInterface
public enum PermissionBit {

    /**
     * No permissions granted — equivalent to zero (bit 0).
     */
    NONE(0),

    /**
     * Grants the ability to view a resource (bit 0).
     *
     * <p>This permission allows users to read and retrieve information from a resource.
     * It is the most basic level of access required for any read operation.</p>
     */
    READ(1),

    /**
     * Grants the ability to create new instances of a resource (bit 1).
     *
     * <p>This permission allows users to create new resources or entities in the system.
     * It is required for any operation that adds new data to the system.</p>
     */
    CREATE(2),

    /**
     * Grants the ability to modify an existing resource (bit 2).
     *
     * <p>This permission allows users to update or change existing resources in the system.
     * It is required for any operation that modifies data that already exists.</p>
     */
    UPDATE(4),

    /**
     * Grants the ability to remove a resource (bit 3).
     *
     * <p>This permission allows users to delete or remove resources from the system.
     * It is required for any operation that permanently removes data from the system.</p>
     */
    DELETE(8),

    /**
     * Convenience constant granting all operations — equivalent to READ | CREATE | UPDATE | DELETE.
     *
     * <p>This constant represents the union of all individual permissions and can be used
     * to grant complete access to a resource. It is equivalent to granting all four basic
     * permissions (READ, CREATE, UPDATE, DELETE) simultaneously.</p>
     */
    ALL(READ.value | CREATE.value | UPDATE.value | DELETE.value);

    /**
     * The integer bit value of this permission.
     * Combine multiple bits via bitwise OR to form a permission mask.
     */
    public final int value;

    PermissionBit(int value) {
        this.value = value;
    }
}
