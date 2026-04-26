package com.cartify.ecommerce.authorization.userrole.domain;

import com.cartify.ecommerce.authorization.role.domain.RoleId;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserRole} entities.
 * Provides data access operations for user role associations in the authorization system.
 */
public interface UserRoleRepository extends Repository<UserRole, UserRoleId> {

    /**
     * Finds all user role associations for a specific user.
     * 
     * @param userId the identifier of the user to find roles for
     * @return a list of UserRole entities associated with the specified user
     */
    List<UserRole> findAllByUserId(@NonNull UserId userId);

    /**
     * Finds all user role associations for a specific role.
     * 
     * @param roleId the identifier of the role to find users for
     * @return a list of UserRole entities associated with the specified role
     */
    List<UserRole> findAllByRoleId(@NonNull RoleId roleId);

    /**
     * Checks if a user role association exists for the specified user and role.
     * 
     * @param userId the identifier of the user
     * @param roleId the identifier of the role
     * @return true if the user-role association exists, false otherwise
     */
    boolean existsByUserIdAndRoleId(@NonNull UserId userId, @NonNull RoleId roleId);

    /**
     * Checks if any user role associations exist for the specified role.
     * 
     * @param roleId the identifier of the role to check
     * @return true if any user-role associations exist for the role, false otherwise
     */
    boolean existsByRoleId(@NonNull RoleId roleId);

    /**
     * Finds a user role association by user and role identifiers.
     * 
     * @param userId the identifier of the user
     * @param roleId the identifier of the role
     * @return an Optional containing the UserRole if found, empty otherwise
     */
    Optional<UserRole> findByUserIdAndRoleId(@NonNull UserId userId, @NonNull RoleId roleId);

    /**
     * Saves the specified user role entity to the repository.
     * 
     * @param userRole the UserRole entity to save
     */
    void save(@NonNull UserRole userRole);

    /**
     * Deletes a user role entity by its identifier.
     * 
     * @param id the identifier of the UserRole to delete
     */
    void deleteById(@NonNull UserRoleId id);
}
