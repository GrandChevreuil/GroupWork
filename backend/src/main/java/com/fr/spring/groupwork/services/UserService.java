package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;
import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File UserService.java
 * This interface defines the contract for user-related operations in the application.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    User saveUser(User user);
    void deleteUser(Long id);
    
    // Nouvelles méthodes pour typeUser et isActive
    List<User> getUsersByTypeUser(ETypeUser typeUser);
    List<User> getActiveUsers();
    List<User> getInactiveUsers();
    User updateUserTypeUser(Long id, ETypeUser typeUser);
    User updateUserStatus(Long id, boolean isActive);
}
