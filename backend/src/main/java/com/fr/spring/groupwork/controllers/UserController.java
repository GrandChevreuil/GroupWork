package com.fr.spring.groupwork.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fr.spring.groupwork.services.UserService;

/**
 * File UserController.java
 * This class handles HTTP requests related to user operations, including retrieving, deleting, and updating users.
 * It provides endpoints for managing user data and their status.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Nouveaux endpoints pour typeUser et isActive
    
    @GetMapping("/type/{typeUser}")
    public List<User> getUsersByTypeUser(@PathVariable ETypeUser typeUser) {
        return userService.getUsersByTypeUser(typeUser);
    }
    
    @GetMapping("/active")
    public List<User> getActiveUsers() {
        return userService.getActiveUsers();
    }
    
    @GetMapping("/inactive")
    public List<User> getInactiveUsers() {
        return userService.getInactiveUsers();
    }
    
    @PutMapping("/{id}/type")
    public ResponseEntity<User> updateUserTypeUser(@PathVariable Long id, @RequestBody ETypeUser typeUser) {
        User updatedUser = userService.updateUserTypeUser(id, typeUser);
        return updatedUser != null ? 
            ResponseEntity.ok(updatedUser) : 
            ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<User> updateUserStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        User updatedUser = userService.updateUserStatus(id, isActive);
        return updatedUser != null ? 
            ResponseEntity.ok(updatedUser) : 
            ResponseEntity.notFound().build();
    }
}
