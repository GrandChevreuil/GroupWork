package com.fr.spring.groupwork.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.repository.RoleRepository;

/**
 * File : RoleServiceImplTest.java
 * Tests unitaires pour la classe RoleServiceImpl
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    /**
     * Test de getAllRoles
     */
    @Test
    public void testGetAllRoles() {
        
        Role adminRole = new Role(ERole.ADMIN_SYSTEM);
        adminRole.setId(1);
        Role userRole = new Role(ERole.TEAM_MEMBER);
        userRole.setId(2);
        List<Role> expectedRoles = Arrays.asList(adminRole, userRole);
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleService.getAllRoles();

        assertThat(actualRoles).isEqualTo(expectedRoles);
        verify(roleRepository).findAll();
    }

    /**
     * Test de getRoleById pour un rôle existant
     */
    @Test
    public void testGetRoleById_WhenRoleExists() {
        
        Integer roleId = 1;
        Role adminRole = new Role(ERole.ADMIN_SYSTEM);
        adminRole.setId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(adminRole));

        Optional<Role> result = roleService.getRoleById(roleId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(adminRole);
        verify(roleRepository).findById(roleId);
    }

    /**
     * Test de getRoleById pour un rôle inexistant
     */
    @Test
    public void testGetRoleById_WhenRoleDoesNotExist() {
        
        Integer roleId = 999;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.getRoleById(roleId);

        assertThat(result).isEmpty();
        verify(roleRepository).findById(roleId);
    }

    /**
     * Test de getRoleByName pour un rôle existant
     */
    @Test
    public void testGetRoleByName_WhenRoleExists() {
        
        ERole roleName = ERole.ADMIN_SYSTEM;
        Role adminRole = new Role(roleName);
        adminRole.setId(1);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(adminRole));

        Optional<Role> result = roleService.getRoleByName(roleName);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(roleName);
        verify(roleRepository).findByName(roleName);
    }

    /**
     * Test de getRoleByName pour un rôle inexistant
     */
    @Test
    public void testGetRoleByName_WhenRoleDoesNotExist() {
        
        ERole roleName = ERole.OPTIONCLASS_STUDENT;
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.getRoleByName(roleName);

        assertThat(result).isEmpty();
        verify(roleRepository).findByName(roleName);
    }
}
