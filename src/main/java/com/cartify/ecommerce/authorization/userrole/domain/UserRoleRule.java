package com.cartify.ecommerce.authorization.userrole.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains constants and rules for user role assignment and management within the authorization system.
 * This class defines error message keys used throughout the user role domain to ensure consistent
 * error handling and messaging when validating role assignments and permissions.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRoleRule {

    /**
     * Error message key indicating that a user role assignment would exceed the assigner's permissions.
     * This occurs when attempting to assign a role that contains permissions not held by the assigner.
     */
    public static final String EXCEEDS_ASSIGNER_RIGHTS_MSG = "user-role.assign.exceeds-assigner-rights";

    /**
     * Error message key indicating that a user role is already assigned to the target user.
     * This prevents duplicate role assignments and ensures proper role management.
     */
    public static final String ALREADY_ASSIGNED_MSG = "user-role.assign.already-assigned";

    /**
     * Error message key indicating that a user role was not found in the system.
     * This is used when attempting to access or modify a non-existent user role association.
     */
    public static final String NOT_FOUND_MSG = "user-role.not-found";
    
}
