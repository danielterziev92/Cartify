package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.identity.passwordreset.domain.PasswordReset;
import com.cartify.ecommerce.identity.user.domain.results.RegisterResult;
import com.cartify.ecommerce.identity.user.domain.results.ResetPasswordResult;
import com.cartify.ecommerce.identity.user.domain.results.VerifyEmailResult;
import com.cartify.ecommerce.identity.verificationcode.domain.VerificationCode;
import com.cartify.ecommerce.shared.domain.exception.BusinessRuleException;
import org.jmolecules.ddd.annotation.Service;
import org.jspecify.annotations.NonNull;

/**
 * Domain service coordinating operations that span multiple aggregates
 * within the identity-bounded context.
 *
 * <p>This service contains pure domain logic only — no repositories,
 * no infrastructure dependencies. Persistence, event dispatching, and
 * external calls (e.g., sending emails) are the responsibility of the
 * application layer.
 *
 * <p>Each method returns a result record that carries all modified aggregates.
 * The caller must persist them and drain their events after the call.
 */
@Service
public class UserDomainService {

    /**
     * Registers a new user account and issues a verification code.
     *
     * <p>Creates a {@link User} in {@code PENDING_VERIFICATION} status and a
     * {@link VerificationCode} for email confirmation. The caller must:
     * <ol>
     *   <li>verify the email is not already taken (via {@code UserRepository.existsByEmail})</li>
     *   <li>hash the raw password before passing it here</li>
     *   <li>persist both aggregates</li>
     *   <li>send the verification email with the code</li>
     * </ol>
     *
     * @param email          validated email address string
     * @param hashedPassword BCrypt-hashed password string
     * @return a {@link RegisterResult} containing the new user and verification code
     */
    public @NonNull RegisterResult register(@NonNull String email, @NonNull String hashedPassword) {
        User user = User.create(email, hashedPassword);
        VerificationCode verificationCode = VerificationCode.create(user.getId());

        return new RegisterResult(user, verificationCode);
    }

    /**
     * Verifies a user's email address using a submitted one-time code.
     *
     * <p>Validates the submitted code against the {@link VerificationCode} aggregate,
     * then transitions the {@link User} to {@code ACTIVE} status. The caller must:
     * <ol>
     *   <li>persist the activated user</li>
     *   <li>delete the consumed verification code</li>
     * </ol>
     *
     * @param verificationCode the persisted verification code aggregate for this user
     * @param submittedCode    the raw code string submitted by the user
     * @param user             the user whose email is being verified
     * @return a {@link VerifyEmailResult} containing the activated user and consumed code
     * @throws BusinessRuleException if the code is expired or does not match
     */
    public @NonNull VerifyEmailResult verifyEmail(
            @NonNull VerificationCode verificationCode,
            @NonNull String submittedCode,
            @NonNull User user
    ) {
        verificationCode.verify(submittedCode);
        user.activate();

        return new VerifyEmailResult(user, verificationCode);
    }

    /**
     * Initiates a password reset flow for the given user.
     *
     * <p>Validates that the account is eligible for a password reset — deleted and
     * blocked accounts cannot request one. Creates and returns a {@link PasswordReset}
     * aggregate with a time-limited token. The caller must:
     * <ol>
     *   <li>check whether an active reset already exists and delete it first</li>
     *   <li>persist the new {@link PasswordReset}</li>
     *   <li>send the reset link email (the token is available via {@link PasswordReset#getToken()})</li>
     * </ol>
     *
     * <p>Per the security policy, the caller must <strong>not</strong> reveal to the
     * client whether the email exists in the system (enumeration attack prevention).
     *
     * @param user the user requesting the password reset
     * @return a new {@link PasswordReset} aggregate
     * @throws BusinessRuleException if the account is blocked or deleted
     */
    public @NonNull PasswordReset initiatePasswordReset(@NonNull User user) {
        UserStatus status = user.getStatus();

        if (status == UserStatus.BLOCKED)
            throw new BusinessRuleException(UserRule.Status.BLOCKED_MSG);

        if (status == UserStatus.DELETED)
            throw new BusinessRuleException(UserRule.Status.DELETED_MSG);

        return PasswordReset.create(user.getId());
    }

    /**
     * Completes a password reset by validating the token and changing the user's password.
     *
     * <p>Validates the submitted token against the {@link PasswordReset} aggregate, then
     * replaces the user's password hash. The caller must:
     * <ol>
     *   <li>hash the new raw password before passing it here</li>
     *   <li>persist the updated user</li>
     *   <li>delete the consumed {@link PasswordReset}</li>
     *   <li>invalidate all active sessions (triggered by {@link UserEvent.PasswordChanged})</li>
     * </ol>
     *
     * @param passwordReset     the persisted password reset aggregate
     * @param submittedToken    the token submitted by the user from the reset link
     * @param user              the user whose password is being reset
     * @param newHashedPassword BCrypt-hashed new password string
     * @return a {@link ResetPasswordResult} containing the updated user and consumed reset
     * @throws BusinessRuleException if the token is invalid, expired, or the attempt limit is exceeded
     */
    public @NonNull ResetPasswordResult resetPassword(
            @NonNull PasswordReset passwordReset,
            @NonNull String submittedToken,
            @NonNull User user,
            @NonNull String newHashedPassword
    ) {
        passwordReset.validateToken(submittedToken);
        user.changePassword(PasswordHash.of(newHashedPassword));

        return new ResetPasswordResult(user, passwordReset);
    }
}
