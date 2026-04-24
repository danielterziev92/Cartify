package com.cartify.ecommerce.identity.user.domain.results;

import com.cartify.ecommerce.identity.passwordreset.domain.PasswordReset;
import com.cartify.ecommerce.identity.user.domain.User;
import com.cartify.ecommerce.identity.user.domain.UserDomainService;
import org.jspecify.annotations.NonNull;

/**
 * Result produced by {@link UserDomainService#resetPassword}.
 *
 * <p>Carries the {@link User} whose password was changed and the
 * consumed {@link PasswordReset}. The caller is responsible for persisting
 * the user, deleting the reset record, and invalidating all active sessions.
 *
 * @param user          the user whose password has been changed
 * @param passwordReset the reset record that was consumed — must be deleted by the caller
 */
public record ResetPasswordResult(
        @NonNull User user,
        @NonNull PasswordReset passwordReset
) {
}
