package com.cartify.ecommerce.authorization.permission.domain;

import com.cartify.ecommerce.shared.domain.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Value object representing a single access permission scoped to a module and an optional sub-resource.
 *
 * <p>A permission combines a {@code module} (e.g. {@code "orders"}), an optional {@code resource}
 * path within that module (e.g. {@code "items.notes"}), and a bitmask of allowed operations
 * defined by {@link PermissionBit}. Both {@code module} and {@code resource} are normalized to
 * lowercase on construction.
 */
public record Permission(@NonNull String module, @Nullable String resource, int mask) implements ValueObject {

    public static final String MODULE_BLANK_MSG = "permission.module.blank";
    public static final String MASK_INVALID_MSG = "permission.mask.invalid";

    /**
     * Compact constructor — normalizes {@code module} and {@code resource} to lowercase
     * and validates that both are non-blank and the mask is valid.
     *
     * @throws InvalidValueException if {@code module} is blank, {@code resource} is blank
     *                               (when provided), or {@code mask} is zero or negative
     */
    public Permission {
        module = module.trim().toLowerCase();
        if (module.isBlank()) throw new InvalidValueException(MODULE_BLANK_MSG);

        if (resource != null) {
            resource = resource.trim().toLowerCase();
            if (resource.isBlank()) resource = null;
        }

        if (mask <= 0 || mask > PermissionBit.ALL.value)
            throw new InvalidValueException(MASK_INVALID_MSG);
    }

    /**
     * Factory method for constructing a {@code Permission} from raw values.
     *
     * @param module   the module name
     * @param resource the optional sub-resource path, or {@code null} for module-level access
     * @param mask     the bitmask of allowed operations
     */
    public static @NonNull Permission of(@NonNull String module, @Nullable String resource, int mask) {
        return new Permission(module, resource, mask);
    }

    /**
     * Factory method for a module-level permission without a specific sub-resource.
     *
     * @param module the module name
     * @param mask   the bitmask of allowed operations
     */
    public static @NonNull Permission ofModule(@NonNull String module, int mask) {
        return new Permission(module, null, mask);
    }

    /**
     * Returns {@code true} if this permission grants the given operation.
     *
     * <p>Example:
     * <pre>
     *   permission.can(PermissionBit.READ) // true if READ bit is set
     *   permission.can(PermissionBit.DELETE) // false if DELETE bit is not set
     * </pre>
     *
     * @param bit the operation to check
     */
    public boolean can(@NonNull PermissionBit bit) {
        if (bit == PermissionBit.ALL) return (this.mask & PermissionBit.ALL.value) == PermissionBit.ALL.value;
        return (this.mask & bit.value) != 0;
    }

    /**
     * Returns {@code true} if this permission covers the entire module
     * without targeting a specific sub-resource.
     */
    public boolean isModuleLevel() {
        return this.resource == null;
    }

    /**
     * Returns the full permission path — module only if no resource,
     * or {@code "module.resource"} if a resource is present.
     *
     * <p>Example: {@code "orders"} or {@code "orders.items.notes"}
     */
    public @NonNull String fullPath() {
        return this.resource == null ? this.module : this.module + "." + this.resource;
    }
}
