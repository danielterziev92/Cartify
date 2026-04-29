package com.cartify.ecommerce.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a constant class as a permission module declaration.
 *
 * <p>Classes annotated with {@code @PermissionModule} are discovered automatically
 * by {@link com.cartify.ecommerce.authorization.permission.application.PermissionRegistrar}
 * at application startup and their declared permissions are registered in the system.
 *
 * <p>Usage:
 * <pre>
 * &#64;PermissionModule("catalog")
 * public final class CatalogPermissions {
 *
 *     &#64;PermissionResource("read")
 *     public static final int READ = PermissionBit.READ.value;
 * }
 * </pre>
 *
 * @see PermissionResource
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionModule {

    /**
     * The module name, e.g. {@code "catalog"}, {@code "orders"}.
     * Normalized to lowercase on registration.
     */
    String value();
}
