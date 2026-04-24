package com.cartify.ecommerce.authorization.permission.domain;

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
 */
public enum PermissionBit {

    /**
     * Grants the ability to view a resource (bit 0).
     */
    READ(1),

    /**
     * Grants the ability to create new instances of a resource (bit 1).
     */
    CREATE(2),

    /**
     * Grants the ability to modify an existing resource (bit 2).
     */
    UPDATE(4),

    /**
     * Grants the ability to remove a resource (bit 3).
     */
    DELETE(8),

    /**
     * Convenience constant granting all operations — equivalent to READ | CREATE | UPDATE | DELETE.
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
