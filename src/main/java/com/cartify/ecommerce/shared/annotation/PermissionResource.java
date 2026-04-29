package com.cartify.ecommerce.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a static {@code int} field within a {@link PermissionModule}-annotated class
 * as a permission resource declaration.
 *
 * <p>The field value must be a valid bitmask composed of {@link com.cartify.ecommerce.authorization.permission.domain.PermissionBit} values.
 * The {@link #value()} attribute defines the sub-resource path within the module
 * (e.g. {@code "items"}, {@code "read.own"}).
 *
 * <p>Usage:
 * <pre>
 * {@code @PermissionModule("orders")}
 * public final class OrderPermissions {
 *
 *     {@code @PermissionResource("items")}
 *     public static final int READ = PermissionBit.READ.value;
 * }
 * </pre>
 *
 * @see PermissionModule
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionResource {

    /**
     * The sub-resource path within the module, e.g. {@code "items"}, {@code "read.own"}.
     * If empty, the permission is treated as module-level (no sub-resource).
     * Normalized to lowercase on registration.
     */
    String value() default "";
}
