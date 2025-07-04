package com.fr.spring.groupwork.security.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fr.spring.groupwork.repository.UserRepository;

/**
 * File UserDetailsServiceImplTest.java
 * Tests unitaires pour la classe UserDetailsServiceImpl
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un utilisateur de test avec un rôle
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setTypeUser(ETypeUser.STUDENT);
        testUser.setActive(true);

        // Ajout d'un rôle à l'utilisateur
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(1);
        role.setName(ERole.TEAM_MEMBER);
        roles.add(role);
        testUser.setRoles(roles);
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserDetails() {
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        
        // Vérifier que c'est bien une instance de UserDetailsImpl
        assertTrue(userDetails instanceof UserDetailsImpl);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        
        // Vérifier les propriétés spécifiques à UserDetailsImpl
        assertEquals(1L, userDetailsImpl.getId());
        assertEquals("test@example.com", userDetailsImpl.getEmail());
        assertEquals(ETypeUser.STUDENT, userDetailsImpl.getTypeUser());
        assertEquals(1, userDetails.getAuthorities().size());
        
        // Vérifier l'appel au repository
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_shouldThrowUsernameNotFoundException() {
        
        String nonExistentUsername = "nonexistent";
        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(nonExistentUsername);
        });

        // Vérifier le message d'erreur
        String expectedMessage = "User Not Found with username: " + nonExistentUsername;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        
        // Vérifier l'appel au repository
        verify(userRepository).findByUsername(nonExistentUsername);
    }
}
