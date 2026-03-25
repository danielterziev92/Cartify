/**
 * Catalog module — manages categories, products, and their variants.
 * This is a Spring Modulith application module; its internal packages are not accessible
 * from outside without going through the module's public API surface.
 */
@ApplicationModule(displayName = "Catalog", allowedDependencies = "shared")
package com.cartify.ecommerce.catalog;

import org.springframework.modulith.ApplicationModule;