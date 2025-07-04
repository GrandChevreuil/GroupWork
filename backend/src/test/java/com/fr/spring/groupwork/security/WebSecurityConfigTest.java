package com.fr.spring.groupwork.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import com.fr.spring.groupwork.security.jwt.AuthTokenFilter;
import com.fr.spring.groupwork.security.jwt.AuthEntryPointJwt;
import com.fr.spring.groupwork.security.services.UserDetailsServiceImpl;
import org.springframework.security.web.DefaultSecurityFilterChain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * File WebSecurityConfigTest.java
 * Tests unitaires pour la configuration de sécurité Web
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {

    private final WebSecurityConfig config = new WebSecurityConfig();

    @Test
    void authTokenFilterBean_shouldReturnNewFilter() {
        AuthTokenFilter filter1 = config.authenticationJwtTokenFilter();
        AuthTokenFilter filter2 = config.authenticationJwtTokenFilter();
        assertThat(filter1).isNotNull().isInstanceOf(AuthTokenFilter.class);
        assertThat(filter2).isNotSameAs(filter1);
    }


    @Test
    void passwordEncoderBean_shouldBeBCrypt() {
        PasswordEncoder pw = config.passwordEncoder();
        assertThat(pw).isInstanceOf(BCryptPasswordEncoder.class);
    }
}
