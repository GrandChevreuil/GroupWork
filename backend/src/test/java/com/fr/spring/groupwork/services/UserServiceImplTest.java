package com.fr.spring.groupwork.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fr.spring.groupwork.repository.UserRepository;

/**
 * File : UserServiceImplTest.java
 * Tests unitaires pour UserServiceImpl
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private User user3;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        // Configuration des objets pour les tests
        user1 = new User("user1", "user1@example.com", "password", ETypeUser.STUDENT);
        user1.setId(1L);
        user1.setActive(true);

        user2 = new User("user2", "user2@example.com", "password", ETypeUser.TEACHER);
        user2.setId(2L);
        user2.setActive(true);

        user3 = new User("user3", "user3@example.com", "password", ETypeUser.OTHER);
        user3.setId(3L);
        user3.setActive(false);

        userList = Arrays.asList(user1, user2, user3);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(user1, user2, user3);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userId);
        assertThat(result.get().getUsername()).isEqualTo("user1");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnEmpty() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserByUsername_whenUserExists_shouldReturnUser() {
        // Given
        String username = "user1";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));

        // When
        Optional<User> result = userService.getUserByUsername(username);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(username);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsername_whenUserDoesNotExist_shouldReturnEmpty() {
        // Given
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getUserByUsername(username);

        // Then
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void saveUser_shouldReturnSavedUser() {
        // Given
        User userToSave = new User("newuser", "newuser@example.com", "password");
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        // When
        User result = userService.saveUser(userToSave);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("newuser");
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void deleteUser_shouldCallRepositoryDeleteById() {
        // Given
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUsersByTypeUser_shouldReturnUsersWithSpecifiedType() {
        // Given
        ETypeUser typeUser = ETypeUser.STUDENT;
        when(userRepository.findByTypeUser(typeUser)).thenReturn(Arrays.asList(user1));

        // When
        List<User> result = userService.getUsersByTypeUser(typeUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTypeUser()).isEqualTo(typeUser);
        verify(userRepository, times(1)).findByTypeUser(typeUser);
    }

    @Test
    void getActiveUsers_shouldReturnOnlyActiveUsers() {
        // Given
        when(userRepository.findByIsActive(true)).thenReturn(Arrays.asList(user1, user2));

        // When
        List<User> result = userService.getActiveUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(User::isActive);
        verify(userRepository, times(1)).findByIsActive(true);
    }

    @Test
    void getInactiveUsers_shouldReturnOnlyInactiveUsers() {
        // Given
        when(userRepository.findByIsActive(false)).thenReturn(Arrays.asList(user3));

        // When
        List<User> result = userService.getInactiveUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(user -> !user.isActive());
        verify(userRepository, times(1)).findByIsActive(false);
    }

    @Test
    void updateUserTypeUser_whenUserExists_shouldUpdateAndReturnUser() {
        // Given
        Long userId = 1L;
        ETypeUser newTypeUser = ETypeUser.TEACHER;
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        
        User updatedUser = new User(user1.getUsername(), user1.getEmail(), user1.getPassword());
        updatedUser.setId(user1.getId());
        updatedUser.setTypeUser(newTypeUser);
        
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        User result = userService.updateUserTypeUser(userId, newTypeUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getTypeUser()).isEqualTo(newTypeUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserTypeUser_whenUserDoesNotExist_shouldReturnNull() {
        // Given
        Long userId = 999L;
        ETypeUser newTypeUser = ETypeUser.TEACHER;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User result = userService.updateUserTypeUser(userId, newTypeUser);

        // Then
        assertThat(result).isNull();
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void updateUserStatus_whenUserExists_shouldUpdateAndReturnUser() {
        // Given
        Long userId = 1L;
        boolean newStatus = false;
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        
        User updatedUser = new User(user1.getUsername(), user1.getEmail(), user1.getPassword());
        updatedUser.setId(user1.getId());
        updatedUser.setActive(newStatus);
        
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        User result = userService.updateUserStatus(userId, newStatus);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.isActive()).isEqualTo(newStatus);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserStatus_whenUserDoesNotExist_shouldReturnNull() {
        // Given
        Long userId = 999L;
        boolean newStatus = false;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User result = userService.updateUserStatus(userId, newStatus);

        // Then
        assertThat(result).isNull();
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }
}
