package com.fr.spring.groupwork.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

/**
 * File TestControllerTest.java
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */
@WebMvcTest(TestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAll_thenReturnPublicContent() throws Exception {
        mockMvc.perform(get("/api/test/all"))
               .andExpect(status().isOk())
               .andExpect(content().string("Public Content."));
    }

    @Test
    void whenGetUser_withStudentAuthority_thenReturnUserContent() throws Exception {
        mockMvc.perform(get("/api/test/user")
                .with(user("u1").authorities(
                    new SimpleGrantedAuthority("OPTIONCLASS_STUDENT"))))
               .andExpect(status().isOk())
               .andExpect(content().string("User Content."));
    }

    @Test
    void whenGetMod_withModeratorAuthority_thenReturnModeratorBoard() throws Exception {
        mockMvc.perform(get("/api/test/mod")
                .with(user("u1").authorities(
                    new SimpleGrantedAuthority("SUPERVISING_STAFF"))))
               .andExpect(status().isOk())
               .andExpect(content().string("Moderator Board."));
    }

    @Test
    void whenGetAdmin_withAdminAuthority_thenReturnAdminBoard() throws Exception {
        mockMvc.perform(get("/api/test/admin")
                .with(user("u1").authorities(
                    new SimpleGrantedAuthority("ADMIN_SYSTEM"))))
               .andExpect(status().isOk())
               .andExpect(content().string("Admin Board."));
    }
}
