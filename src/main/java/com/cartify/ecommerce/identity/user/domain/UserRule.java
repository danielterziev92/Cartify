package com.cartify.ecommerce.identity.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Validation constants and i18n message keys for the {@link User} aggregate.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRule {

    /**
     * Constraints and message keys for the hashed password field.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Password {
        /**
         * Expected length of a BCrypt hash in characters.
         * A BCrypt hash has the format {@code $2a$} or {@code $2b$} followed by
         * a two-digit cost factor, {@code $}, and 53 Base64 characters — 60 total.
         */
        public static final int LENGTH = 60;

        /**
         * Regular expression used to validate the format of a BCrypt hash.
         * Accepts both the {@code $2a$} and {@code $2b$} variants with any two-digit
         * cost factor and a 53-character Base64 payload.
         */
        public static final Pattern PATTERN = Pattern.compile("^\\$2[ab]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");

        /**
         * i18n key used when the supplied password hash is blank.
         */
        public static final String BLANK_MSG = "user.password.blank";
        /**
         * i18n key used when the hash length does not equal {@link #LENGTH}.
         */
        public static final String LENGTH_MSG = "user.password.length";
        /**
         * i18n key used when the hash does not match {@link #PATTERN}.
         */
        public static final String INVALID_MSG = "user.password.invalid";
    }

    /**
     * Message keys for rejected {@link UserStatus} transitions.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Status {
        /**
         * i18n key used when the account is still awaiting email verification.
         */
        public static final String PENDING_VERIFICATION_MSG = "user.status.pending-verification";
        /**
         * i18n key used when the account has been deactivated.
         */
        public static final String INACTIVE_MSG = "user.status.inactive";
        /**
         * i18n key used when the account has been blocked.
         */
        public static final String BLOCKED_MSG = "user.status.blocked";
        /**
         * i18n key used when the account has been deleted.
         */
        public static final String DELETED_MSG = "user.status.deleted";
    }
}
