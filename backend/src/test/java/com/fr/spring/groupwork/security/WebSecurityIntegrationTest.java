package com.fr.spring.groupwork.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * File WebSecurityIntegrationTest.java
 * Tests d'intégration pour la sécurité Web
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_shouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/auth/nonexistent"))
               .andExpect(status().isNotFound());
        mockMvc.perform(get("/api/test/all"))
               .andExpect(status().isOk());
        mockMvc.perform(get("/api/roles"))
               .andExpect(status().isOk());
    }

    @Test
    void protectedEndpoints_shouldReturnUnauthorizedWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/test/user"))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/test/mod"))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/test/admin"))
               .andExpect(status().isUnauthorized());
        // any other endpoint
        mockMvc.perform(get("/api/secure/unknown"))
               .andExpect(status().isUnauthorized());
    }
}
