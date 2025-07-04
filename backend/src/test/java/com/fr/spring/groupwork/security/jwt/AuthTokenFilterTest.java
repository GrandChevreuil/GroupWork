package com.fr.spring.groupwork.security.jwt;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.fr.spring.groupwork.security.services.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.fr.spring.groupwork.models.enums.ETypeUser;

import java.util.Collections;

/**
 * File : AuthTokenFilterTest.java
 * Tests unitaires pour la classe AuthTokenFilter
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class AuthTokenFilterTest {
    
    private AuthTokenFilter authTokenFilter;

    @Mock
    private IJwtUtils jwtUtils;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;
    
    private SecurityContext securityContext;
    
    private UserDetails userDetails;
    
    @BeforeEach
    void setUp() throws Exception {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
        
        // Création de l'utilisateur pour les tests
        userDetails = new UserDetailsImpl(1L, "testUser", "test@example.com", "password",
                Collections.emptyList(), ETypeUser.STUDENT, true);

        // Initialisation d'un SecurityContext réel
        securityContext = new SecurityContextImpl();
        SecurityContextHolder.setContext(securityContext);
    
        authTokenFilter = new AuthTokenFilter();
        
        // Injection des dépendances via réflexion
        injectFieldIntoFilter("jwtUtils", jwtUtils);
        injectFieldIntoFilter("userDetailsService", userDetailsService);
    }
    
    // Méthode utilitaire pour injecter des champs via réflexion
    private void injectFieldIntoFilter(String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = AuthTokenFilter.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(authTokenFilter, value);
    }
    
    @Test
    void doFilterInternal_whenValidJwt_shouldAuthenticate() throws ServletException, IOException {
        String token = "valid.jwt.token";
        
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn("testUser");
        
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        
        // Capture le contexte de sécurité avant l'exécution
        SecurityContext contextBeforeFilter = SecurityContextHolder.getContext();
        assertThat(contextBeforeFilter.getAuthentication()).isNull();
        
        authTokenFilter.doFilterInternal(request, response, filterChain);
        
        verify(jwtUtils).getJwtFromCookies(request);
        verify(jwtUtils).validateJwtToken(token);
        verify(jwtUtils).getUserNameFromJwtToken(token);
        verify(userDetailsService).loadUserByUsername("testUser");
        
        // Vérifie que l'authentification a été définie dans le contexte de sécurité
        SecurityContext contextAfterFilter = SecurityContextHolder.getContext();
        assertThat(contextAfterFilter.getAuthentication()).isNotNull();
        assertThat(contextAfterFilter.getAuthentication().getPrincipal()).isEqualTo(userDetails);
        assertThat(contextAfterFilter.getAuthentication().getAuthorities()).isEqualTo(userDetails.getAuthorities());
        
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    void doFilterInternal_whenNoJwt_shouldNotAuthenticate() throws ServletException, IOException {
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(null);
        
        authTokenFilter.doFilterInternal(request, response, filterChain);
        
        verify(jwtUtils).getJwtFromCookies(request);
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(jwtUtils, never()).getUserNameFromJwtToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    void doFilterInternal_whenInvalidJwt_shouldNotAuthenticate() throws ServletException, IOException {
        String token = "invalid.jwt.token";
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);
        
        authTokenFilter.doFilterInternal(request, response, filterChain);
        
        verify(jwtUtils).getJwtFromCookies(request);
        verify(jwtUtils).validateJwtToken(token);
        verify(jwtUtils, never()).getUserNameFromJwtToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    void doFilterInternal_whenExceptionThrown_shouldContinueChain() throws ServletException, IOException {
        String token = "exception.token";
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenThrow(new RuntimeException("Test exception"));
        
        // Capture le contexte de sécurité avant l'exécution
        SecurityContext contextBeforeFilter = SecurityContextHolder.getContext();
        
        authTokenFilter.doFilterInternal(request, response, filterChain);
        
        verify(jwtUtils).getJwtFromCookies(request);
        verify(jwtUtils).validateJwtToken(token);
        
        // Vérifie que l'authentification n'a pas été modifiée en cas d'erreur
        SecurityContext contextAfterFilter = SecurityContextHolder.getContext();
        assertThat(contextAfterFilter.getAuthentication()).isEqualTo(contextBeforeFilter.getAuthentication());
        
        // Vérifie que le filtre continue la chaîne malgré l'exception
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    void doFilterInternal_withWebAuthenticationDetails_shouldSetCorrectly() throws ServletException, IOException {
        String token = "valid.jwt.token";
        
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        
        authTokenFilter.doFilterInternal(request, response, filterChain);
        
        // Vérifie les appels corrects aux méthodes
        verify(jwtUtils).getJwtFromCookies(request);
        verify(jwtUtils).validateJwtToken(token);
        verify(jwtUtils).getUserNameFromJwtToken(token);
        verify(userDetailsService).loadUserByUsername("testUser");
        
        // Vérifie que l'authentification a été définie dans le contexte de sécurité
        SecurityContext contextAfterFilter = SecurityContextHolder.getContext();
        assertThat(contextAfterFilter.getAuthentication()).isNotNull();
        assertThat(contextAfterFilter.getAuthentication().getPrincipal()).isEqualTo(userDetails);
        assertThat(contextAfterFilter.getAuthentication().getAuthorities()).isEqualTo(userDetails.getAuthorities());
        
        // Vérifie que la chaîne de filtres continue
        verify(filterChain).doFilter(request, response);
    }
}
