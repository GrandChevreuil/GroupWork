package com.fr.spring.groupwork.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fr.spring.groupwork.controllers.AuthController;
import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.payload.request.LoginRequest;
import com.fr.spring.groupwork.payload.request.SignupRequest;
import com.fr.spring.groupwork.payload.response.MessageResponse;
import com.fr.spring.groupwork.security.jwt.IJwtUtils;
import com.fr.spring.groupwork.security.services.UserDetailsImpl;
import com.fr.spring.groupwork.repository.RoleRepository;
import com.fr.spring.groupwork.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * File AuthControllerTest.java
 * This class tests the AuthController endpoints for user authentication.
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private UserRepository userRepository;
    @MockBean private RoleRepository roleRepository;
    @MockBean private PasswordEncoder encoder;
    @MockBean private IJwtUtils jwtUtils;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void signin_shouldReturnUserInfoAndCookie() throws Exception {
        // Préparer userDetails
        User user = new User("user1", "user1@example.com", "password123");
        user.setId(5L);
        when(authenticationManager.authenticate(any(Authentication.class)))
            .thenReturn(new UsernamePasswordAuthenticationToken(
                UserDetailsImpl.build(user), null, Collections.emptyList()));
        ResponseCookie cookie = ResponseCookie.from("jwt", "token").path("/").httpOnly(true).build();
        when(jwtUtils.generateJwtCookie(any(UserDetailsImpl.class))).thenReturn(cookie);

        LoginRequest req = new LoginRequest();
        req.setUsername("user1");
        req.setPassword("password123");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.SET_COOKIE, cookie.toString()))
               .andExpect(jsonPath("$.username").value("user1"))
               .andExpect(jsonPath("$.email").value("user1@example.com"));
    }

    @Test
    void signup_usernameTaken_shouldBadRequest() throws Exception {
        when(userRepository.existsByUsername("user1")).thenReturn(true);
        SignupRequest req = new SignupRequest();
        req.setUsername("user1"); req.setEmail("e@e.com"); req.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
    }

    @Test
    void signup_emailTaken_shouldBadRequest() throws Exception {
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("e@e.com")).thenReturn(true);
        SignupRequest req = new SignupRequest();
        req.setUsername("user1"); req.setEmail("e@e.com"); req.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("Error: Email is already in use!"));
    }

    @Test
    void signup_defaultRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("user2")).thenReturn(false);
        when(userRepository.existsByEmail("e2@e.com")).thenReturn(false);
        Role def = new Role(ERole.OPTIONCLASS_STUDENT);
        when(roleRepository.findByName(ERole.OPTIONCLASS_STUDENT)).thenReturn(Optional.of(def));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("user2"); req.setEmail("e2@e.com"); req.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void signup_customRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("user3")).thenReturn(false);
        when(userRepository.existsByEmail("e3@e.com")).thenReturn(false);
        Role admin = new Role(ERole.ADMIN_SYSTEM);
        when(roleRepository.findByName(ERole.ADMIN_SYSTEM)).thenReturn(Optional.of(admin));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("user3"); req.setEmail("e3@e.com"); req.setPassword("password123");
        req.setRole(Set.of("admin_system"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void signout_shouldClearCookieAndReturnMessage() throws Exception {
        ResponseCookie cookie = ResponseCookie.from("jwt", "").path("/").build();
        when(jwtUtils.getCleanJwtCookie()).thenReturn(cookie);

        mockMvc.perform(post("/api/auth/signout"))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.SET_COOKIE, cookie.toString()))
               .andExpect(jsonPath("$.message").value("You've been signed out!"));
    }

    @Test
    void signup_adminProjectRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userProject")).thenReturn(false);
        when(userRepository.existsByEmail("admin.project@e.com")).thenReturn(false);
        Role adminProject = new Role(ERole.ADMIN_PROJECT);
        when(roleRepository.findByName(ERole.ADMIN_PROJECT)).thenReturn(Optional.of(adminProject));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userProject");
        req.setEmail("admin.project@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("admin_project"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void signup_adminOptionRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userOption")).thenReturn(false);
        when(userRepository.existsByEmail("admin.option@e.com")).thenReturn(false);
        Role adminOption = new Role(ERole.ADMIN_OPTION);
        when(roleRepository.findByName(ERole.ADMIN_OPTION)).thenReturn(Optional.of(adminOption));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userOption");
        req.setEmail("admin.option@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("admin_option"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_supervisingStaffRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userStaff")).thenReturn(false);
        when(userRepository.existsByEmail("supervising.staff@e.com")).thenReturn(false);
        Role supervisingStaff = new Role(ERole.SUPERVISING_STAFF);
        when(roleRepository.findByName(ERole.SUPERVISING_STAFF)).thenReturn(Optional.of(supervisingStaff));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userStaff");
        req.setEmail("supervising.staff@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("supervising_staff"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_optionclassStudentRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userStudent")).thenReturn(false);
        when(userRepository.existsByEmail("optionclass.student@e.com")).thenReturn(false);
        Role optionclassStudent = new Role(ERole.OPTIONCLASS_STUDENT);
        when(roleRepository.findByName(ERole.OPTIONCLASS_STUDENT)).thenReturn(Optional.of(optionclassStudent));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userStudent");
        req.setEmail("optionclass.student@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("optionclass_student"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_teamMemberRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userMember")).thenReturn(false);
        when(userRepository.existsByEmail("team.member@e.com")).thenReturn(false);
        Role teamMember = new Role(ERole.TEAM_MEMBER);
        when(roleRepository.findByName(ERole.TEAM_MEMBER)).thenReturn(Optional.of(teamMember));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userMember");
        req.setEmail("team.member@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("team_member"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_coachesRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userCoach")).thenReturn(false);
        when(userRepository.existsByEmail("coaches@e.com")).thenReturn(false);
        Role coaches = new Role(ERole.COACHES);
        when(roleRepository.findByName(ERole.COACHES)).thenReturn(Optional.of(coaches));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userCoach");
        req.setEmail("coaches@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("coaches"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_juryMembersRole_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userJury")).thenReturn(false);
        when(userRepository.existsByEmail("jury.members@e.com")).thenReturn(false);
        Role juryMembers = new Role(ERole.JURY_MEMBERS);
        when(roleRepository.findByName(ERole.JURY_MEMBERS)).thenReturn(Optional.of(juryMembers));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        SignupRequest req = new SignupRequest();
        req.setUsername("userJury");
        req.setEmail("jury.members@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("jury_members"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_invalidRole_shouldReturnBadRequest() throws Exception {
        when(userRepository.existsByUsername("userInvalid")).thenReturn(false);
        when(userRepository.existsByEmail("invalid.role@e.com")).thenReturn(false);
        // Simuler une exception pour un rôle invalide
        when(roleRepository.findByName(any())).thenReturn(Optional.empty());
        
        SignupRequest req = new SignupRequest();
        req.setUsername("userInvalid");
        req.setEmail("invalid.role@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("invalid_role"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void signup_multipleRoles_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userMulti")).thenReturn(false);
        when(userRepository.existsByEmail("multi.roles@e.com")).thenReturn(false);
        
        Role adminSystem = new Role(ERole.ADMIN_SYSTEM);
        Role adminProject = new Role(ERole.ADMIN_PROJECT);
        
        when(roleRepository.findByName(ERole.ADMIN_SYSTEM)).thenReturn(Optional.of(adminSystem));
        when(roleRepository.findByName(ERole.ADMIN_PROJECT)).thenReturn(Optional.of(adminProject));
        
        when(encoder.encode("password123")).thenReturn("pwdHash");
        
        SignupRequest req = new SignupRequest();
        req.setUsername("userMulti");
        req.setEmail("multi.roles@e.com");
        req.setPassword("password123");
        req.setRole(Set.of("admin_system", "admin_project"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    void signup_withTypeUser_shouldRegister() throws Exception {
        when(userRepository.existsByUsername("userTeacher")).thenReturn(false);
        when(userRepository.existsByEmail("teacher@e.com")).thenReturn(false);
        Role defaultRole = new Role(ERole.OPTIONCLASS_STUDENT);
        when(roleRepository.findByName(ERole.OPTIONCLASS_STUDENT)).thenReturn(Optional.of(defaultRole));
        when(encoder.encode("password123")).thenReturn("pwdHash");
        
        SignupRequest req = new SignupRequest();
        req.setUsername("userTeacher");
        req.setEmail("teacher@e.com");
        req.setPassword("password123");
        req.setTypeUser(com.fr.spring.groupwork.models.enums.ETypeUser.TEACHER);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
}
