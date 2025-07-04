package com.fr.spring.groupwork.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File : UserTest.java
 * Tests unitaires pour la classe User
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class UserTest {

    /**
     * Test des getters et setters
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        User user = new User();
        Long id = 1L;
        String username = "testUser";
        String email = "test@example.com";
        String password = "securePassword123";
        ETypeUser typeUser = ETypeUser.TEACHER;
        boolean isActive = true;
        Classe classe = new Classe();
        classe.setId(1L);
        classe.setName("Test Class");
        
        // Act
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setTypeUser(typeUser);
        user.setActive(isActive);
        user.setClasse(classe);
        
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ERole.TEAM_MEMBER);
        roles.add(role);
        user.setRoles(roles);
        
        // Assert
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getTypeUser()).isEqualTo(typeUser);
        assertThat(user.isActive()).isEqualTo(isActive);
        assertThat(user.getClasse()).isEqualTo(classe);
        assertThat(user.getRoles()).isEqualTo(roles);
    }
    
    /**
     * Test spécifique pour le setter setPassword
     */
    @Test
    void testSetPassword() {
        // Arrange
        User user = new User();
        String initialPassword = "initialPassword";
        String newPassword = "newSecurePassword";
        
        // Act - définir le mot de passe initial
        user.setPassword(initialPassword);
        assertThat(user.getPassword()).isEqualTo(initialPassword);
        
        // Act - changer le mot de passe
        user.setPassword(newPassword);
        
        // Assert - vérifier que le nouveau mot de passe est bien défini
        assertThat(user.getPassword()).isEqualTo(newPassword);
        assertThat(user.getPassword()).isNotEqualTo(initialPassword);
    }
    
    /**
     * Test du constructeur par défaut
     */
    @Test
    void testDefaultConstructor() {
        // Act
        User user = new User();
        
        // Assert - vérifier que l'objet est créé et non null
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getRoles()).isNotNull();
        assertThat(user.getRoles()).isEmpty();
        assertThat(user.getTypeUser()).isNull();
        assertThat(user.getClasse()).isNull();
        assertThat(user.isActive()).isTrue();
    }
}
