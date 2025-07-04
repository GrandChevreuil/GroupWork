package com.fr.spring.groupwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File UserRepository.java
 * This interface extends JpaRepository to provide CRUD operations for User entities.
 * It includes methods to find users by username, email, typeUser, and isActive status.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  // Nouvelles méthodes pour typeUser et isActive
  List<User> findByTypeUser(ETypeUser typeUser);
  
  List<User> findByIsActive(boolean isActive);
}
