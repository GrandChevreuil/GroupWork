package com.fr.spring.groupwork.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fr.spring.groupwork.models.enums.ERole;

/**
 * File : RoleTest.java
 * Tests unitaires pour la classe Role
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class RoleTest {

    /**
     * Test du constructeur par défaut et des setters
     */
    @Test
    void testDefaultConstructorAndSetters() {
        
        Integer id = 1;
        ERole name = ERole.ADMIN_SYSTEM;
        
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        
        assertThat(role.getId()).isEqualTo(id);
        assertThat(role.getName()).isEqualTo(name);
    }
    
    /**
     * Test du constructeur avec paramètre
     */
    @Test
    void testConstructorWithName() {
        
        ERole name = ERole.TEAM_MEMBER;
        
        Role role = new Role(name);
        
        assertThat(role.getName()).isEqualTo(name);
        assertThat(role.getId()).isNull(); // L'ID n'est pas défini par le constructeur
    }
    
    /**
     * Test spécifique du setter setName
     */
    @Test
    void testSetName() {
        
        Role role = new Role(ERole.TEAM_MEMBER);
        ERole newName = ERole.ADMIN_SYSTEM;
        
        role.setName(newName);
        
        assertThat(role.getName()).isEqualTo(newName);
        assertThat(role.getName()).isNotEqualTo(ERole.TEAM_MEMBER);
    }
    
    /**
     * Test de modification de l'ID
     */
    @Test
    void testSetId() {
        
        Role role = new Role(ERole.OPTIONCLASS_STUDENT);
        Integer id = 5;
        
        role.setId(id);
        
        assertThat(role.getId()).isEqualTo(id);
    }
}
