package com.fr.spring.groupwork.payload.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File : UserInfoResponseTest.java
 * Tests unitaires pour la classe UserInfoResponse
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

public class UserInfoResponseTest {
    
    /**
     * Test du constructeur avec 4 paramètres
     */
    @Test
    public void testConstructorWith4Parameters() {
        
        Long id = 1L;
        String username = "testUser";
        String email = "test@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        
        UserInfoResponse response = new UserInfoResponse(id, username, email, roles);
        
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getUsername()).isEqualTo(username);
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getRoles()).isEqualTo(roles);
        // Les autres champs doivent avoir leurs valeurs par défaut
        assertThat(response.getTypeUser()).isNull();
        assertThat(response.isActive()).isFalse();
        assertThat(response.getClasseId()).isNull();
        assertThat(response.getClasseName()).isNull();
    }
    
    /**
     * Test du constructeur par défaut et des setters
     */
    @Test
    public void testDefaultConstructorAndSetters() {
        
        Long id = 2L;
        String username = "anotherUser";
        String email = "another@example.com";
        List<String> roles = Arrays.asList("ROLE_USER");
        ETypeUser typeUser = ETypeUser.STUDENT;
        boolean isActive = true;
        Long classeId = 10L;
        String classeName = "Class A";
        
        UserInfoResponse response = new UserInfoResponse();
        response.setId(id);
        response.setUsername(username);
        response.setEmail(email);
        response.setRoles(roles);
        response.setTypeUser(typeUser);
        response.setActive(isActive);
        response.setClasseId(classeId);
        response.setClasseName(classeName);
        
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getUsername()).isEqualTo(username);
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getRoles()).isEqualTo(roles);
        assertThat(response.getTypeUser()).isEqualTo(typeUser);
        assertThat(response.isActive()).isEqualTo(isActive);
        assertThat(response.getClasseId()).isEqualTo(classeId);
        assertThat(response.getClasseName()).isEqualTo(classeName);
    }
}
