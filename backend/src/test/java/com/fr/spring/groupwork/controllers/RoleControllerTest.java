package com.fr.spring.groupwork.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.services.RoleService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * File RoleControllerTest.java
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    void getAllRoles_shouldReturnList() throws Exception {
        Role r1 = new Role(ERole.ADMIN_SYSTEM);
        Role r2 = new Role(ERole.OPTIONCLASS_STUDENT);
        when(roleService.getAllRoles()).thenReturn(Arrays.asList(r1, r2));

        mockMvc.perform(get("/api/roles"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("ADMIN_SYSTEM"))
               .andExpect(jsonPath("$[1].name").value("OPTIONCLASS_STUDENT"));
    }

    @Test
    void getRoleById_whenFound_thenReturnRole() throws Exception {
        Role r = new Role(ERole.ADMIN_SYSTEM);
        r.setId(5);
        when(roleService.getRoleById(5)).thenReturn(Optional.of(r));

        mockMvc.perform(get("/api/roles/5"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("ADMIN_SYSTEM"));
    }

    @Test
    void getRoleById_whenNotFound_thenReturn404() throws Exception {
        when(roleService.getRoleById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/99"))
               .andExpect(status().isNotFound());
    }
}
