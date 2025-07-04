package com.fr.spring.groupwork.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fr.spring.groupwork.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * File UserControllerTest.java
 * Tests unitaires pour le contrôleur UserController
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // Helper pour créer un utilisateur de test
    private User createTestUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(username + "@example.com");
        user.setTypeUser(ETypeUser.STUDENT);
        user.setActive(true);
        user.setRoles(new HashSet<>());
        return user;
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
                User user1 = createTestUser(1L, "user1");
        User user2 = createTestUser(2L, "user2");
        List<User> users = Arrays.asList(user1, user2);
        
        when(userService.getAllUsers()).thenReturn(users);
        
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"));
                
        verify(userService, times(1)).getAllUsers();
    }
    
    @Test
    void getUserById_whenFound_shouldReturnUser() throws Exception {
        Long userId = 1L;
        User user = createTestUser(userId, "testuser");
        
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        
                mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
                
        verify(userService, times(1)).getUserById(userId);
    }
    
    @Test
    void getUserById_whenNotFound_shouldReturnNotFound() throws Exception {
                Long userId = 999L;
        
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        
                mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
        verify(userService, times(1)).getUserById(userId);
    }
    
    @Test
    void getUserByUsername_whenFound_shouldReturnUser() throws Exception {
                String username = "testuser";
        User user = createTestUser(1L, username);
        
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));
        
                mockMvc.perform(get("/api/users/username/{username}", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(username));
                
        verify(userService, times(1)).getUserByUsername(username);
    }
    
    @Test
    void getUserByUsername_whenNotFound_shouldReturnNotFound() throws Exception {
                String username = "nonexistent";
        
        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());
        
                mockMvc.perform(get("/api/users/username/{username}", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
        verify(userService, times(1)).getUserByUsername(username);
    }
    
    @Test
    void deleteUser_whenFound_shouldReturnNoContent() throws Exception {
                Long userId = 1L;
        User user = createTestUser(userId, "testuser");
        
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userService).deleteUser(userId);
        
                mockMvc.perform(delete("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
                
        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).deleteUser(userId);
    }
    
    @Test
    void deleteUser_whenNotFound_shouldReturnNotFound() throws Exception {
                Long userId = 999L;
        
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        
                mockMvc.perform(delete("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
        verify(userService, times(1)).getUserById(userId);
        verify(userService, never()).deleteUser(userId);
    }
    
    @Test
    void getUsersByTypeUser_shouldReturnUsersWithSpecifiedType() throws Exception {
                ETypeUser typeUser = ETypeUser.STUDENT;
        User user1 = createTestUser(1L, "student1");
        User user2 = createTestUser(2L, "student2");
        List<User> students = Arrays.asList(user1, user2);
        
        when(userService.getUsersByTypeUser(typeUser)).thenReturn(students);
        
                mockMvc.perform(get("/api/users/type/{typeUser}", typeUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("student1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("student2"));
                
        verify(userService, times(1)).getUsersByTypeUser(typeUser);
    }
    
    @Test
    void getActiveUsers_shouldReturnActiveUsers() throws Exception {
                User user1 = createTestUser(1L, "active1");
        User user2 = createTestUser(2L, "active2");
        List<User> activeUsers = Arrays.asList(user1, user2);
        
        when(userService.getActiveUsers()).thenReturn(activeUsers);
        
                mockMvc.perform(get("/api/users/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("active1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("active2"));
                
        verify(userService, times(1)).getActiveUsers();
    }
    
    @Test
    void getInactiveUsers_shouldReturnInactiveUsers() throws Exception {
                User user1 = createTestUser(1L, "inactive1");
        User user2 = createTestUser(2L, "inactive2");
        user1.setActive(false);
        user2.setActive(false);
        List<User> inactiveUsers = Arrays.asList(user1, user2);
        
        when(userService.getInactiveUsers()).thenReturn(inactiveUsers);
        
                mockMvc.perform(get("/api/users/inactive")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("inactive1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("inactive2"));
                
        verify(userService, times(1)).getInactiveUsers();
    }
    
    @Test
    void updateUserTypeUser_whenFound_shouldUpdateAndReturnUser() throws Exception {
                Long userId = 1L;
        ETypeUser newType = ETypeUser.TEACHER;
        User updatedUser = createTestUser(userId, "testuser");
        updatedUser.setTypeUser(newType);
        
        when(userService.updateUserTypeUser(userId, newType)).thenReturn(updatedUser);
        
                mockMvc.perform(put("/api/users/{id}/type", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.typeUser").value(newType.toString()));
                
        verify(userService, times(1)).updateUserTypeUser(userId, newType);
    }
    
    @Test
    void updateUserTypeUser_whenNotFound_shouldReturnNotFound() throws Exception {
                Long userId = 999L;
        ETypeUser newType = ETypeUser.TEACHER;
        
        when(userService.updateUserTypeUser(userId, newType)).thenReturn(null);
        
                mockMvc.perform(put("/api/users/{id}/type", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newType)))
                .andExpect(status().isNotFound());
                
        verify(userService, times(1)).updateUserTypeUser(userId, newType);
    }
    
    @Test
    void updateUserStatus_whenFound_shouldUpdateAndReturnUser() throws Exception {
                Long userId = 1L;
        boolean newStatus = false;
        User updatedUser = createTestUser(userId, "testuser");
        updatedUser.setActive(newStatus);
        
        when(userService.updateUserStatus(userId, newStatus)).thenReturn(updatedUser);
        
                mockMvc.perform(put("/api/users/{id}/status", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.active").value(newStatus));
                
        verify(userService, times(1)).updateUserStatus(userId, newStatus);
    }
    
    @Test
    void updateUserStatus_whenNotFound_shouldReturnNotFound() throws Exception {
                Long userId = 999L;
        boolean newStatus = false;
        
        when(userService.updateUserStatus(userId, newStatus)).thenReturn(null);
        
                mockMvc.perform(put("/api/users/{id}/status", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isNotFound());
                
        verify(userService, times(1)).updateUserStatus(userId, newStatus);
    }
}
