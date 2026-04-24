package com.cartify.ecommerce.authorization.role.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Namespace for domain validation rules and error message keys applied to {@link Role} aggregates.
 *
 * <p>Each nested class groups the constraints and corresponding i18n message keys
 * for a specific aspect of the role domain.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleRule {

    /**
     * Validation rules for the role name field.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {
        /**
         * Maximum allowed length of a role name in characters.
         */
        public static final int MAX_LENGTH = 50;

        /**
         * Only Unicode letters and spaces are allowed.
         * Digits, punctuation, and special characters are rejected.
         */
        public static final Pattern PATTERN = Pattern.compile("^[\\p{L} ]+$");

        /**
         * Error key used when the role name is blank.
         */
        public static final String BLANK_MSG = "role.name.blank";

        /**
         * Error key used when the role name exceeds {@link #MAX_LENGTH} characters.
         */
        public static final String MAX_LENGTH_MSG = "role.name.max-length";

        /**
         * Error key used when the role name does not match {@link #PATTERN}.
         */
        public static final String INVALID_MSG = "role.name.invalid";
    }

    /**
     * Validation rules for system-managed roles.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class System {
        /**
         * Error key used when an attempt is made to mutate an immutable system role.
         */
        public static final String IMMUTABLE_MSG = "role.system.immutable";
    }
}
