package com.cartify.ecommerce.authorization.permission.application.response;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record PermissionResponse(
        @NonNull String module,
        @Nullable String resource,
        int mask,
        @NonNull String fullPath
) {

    public static @NonNull PermissionResponse from(@NonNull Permission permission) {
        return new PermissionResponse(
                permission.module(),
                permission.resource(),
                permission.mask(),
                permission.fullPath()
        );
    }
}
