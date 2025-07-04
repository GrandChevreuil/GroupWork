package com.fr.spring.groupwork.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.WebUtils;

import com.fr.spring.groupwork.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * File : JwtUtilsTest.java
 * Tests unitaires pour la classe JwtUtils
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;
    
    private final String jwtSecret = "testSecretKey";
    private final int jwtExpirationMs = 3600000;
    private final String jwtCookie = "testCookie";
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", jwtExpirationMs);
        ReflectionTestUtils.setField(jwtUtils, "jwtCookie", jwtCookie);
    }
    
    @Test
    void getJwtFromCookies_whenCookieExists_shouldReturnValue() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie cookie = new Cookie(jwtCookie, "testJwtToken");
        request.setCookies(cookie);
        
        String result = jwtUtils.getJwtFromCookies(request);
        
        assertThat(result).isEqualTo("testJwtToken");
    }
    
    @Test
    void getJwtFromCookies_whenCookieDoesNotExist_shouldReturnNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        
        String result = jwtUtils.getJwtFromCookies(request);
        
        assertThat(result).isNull();
    }
    
    private class TestJwtUtils extends JwtUtils {
        @Override
        public String generateTokenFromUsername(String username) {
            return "generatedToken";
        }
    }
    
    @Test
    void generateJwtCookie_shouldReturnProperCookie() {
        User testUser = new User("testUser", "test@example.com", "password");
        testUser.setId(1L);
        
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        UserDetailsImpl userDetails = new UserDetailsImpl(
            1L, "testUser", "test@example.com", "password", 
            authorities, ETypeUser.STUDENT, true
        );
        
        TestJwtUtils testJwtUtils = new TestJwtUtils();
        ReflectionTestUtils.setField(testJwtUtils, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(testJwtUtils, "jwtExpirationMs", jwtExpirationMs);
        ReflectionTestUtils.setField(testJwtUtils, "jwtCookie", jwtCookie);
        
        ResponseCookie responseCookie = testJwtUtils.generateJwtCookie(userDetails);
        
        assertThat(responseCookie).isNotNull();
        assertThat(responseCookie.getName()).isEqualTo(jwtCookie);
        assertThat(responseCookie.getValue()).isEqualTo("generatedToken");
        assertThat(responseCookie.getPath()).isEqualTo("/api");
        assertThat(responseCookie.isHttpOnly()).isTrue();
        assertThat(responseCookie.getMaxAge().getSeconds()).isEqualTo(24L * 60 * 60);
    }
    
    @Test
    void getCleanJwtCookie_shouldReturnEmptyCookie() {
        ResponseCookie responseCookie = jwtUtils.getCleanJwtCookie();
        
        assertThat(responseCookie).isNotNull();
        assertThat(responseCookie.getName()).isEqualTo(jwtCookie);
        assertThat(responseCookie.getValue()).isEmpty();
        assertThat(responseCookie.getPath()).isEqualTo("/api");
        assertThat(responseCookie.isHttpOnly()).isTrue();
        assertThat(responseCookie.getMaxAge().getSeconds()).isZero();
    }
    
    @Test
    void getUserNameFromJwtToken_shouldReturnUsername() {
        String username = "testUser";
        String token = createTestJwt(username);
        
        String result = jwtUtils.getUserNameFromJwtToken(token);
        
        assertThat(result).isEqualTo(username);
    }
    
    @Test
    void validateJwtToken_withValidToken_shouldReturnTrue() {
        String token = createTestJwt("testUser");
        
        boolean result = jwtUtils.validateJwtToken(token);
        
        assertThat(result).isTrue();
    }
    
    @Test
    void validateJwtToken_withInvalidSignature_shouldReturnFalse() {
        String differentSecret = "differentSecret";
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, differentSecret)
                .compact();
        
        boolean result = jwtUtils.validateJwtToken(token);
        
        assertThat(result).isFalse();
    }
    
    @Test
    void validateJwtToken_withMalformedToken_shouldReturnFalse() {
        boolean result = jwtUtils.validateJwtToken("malformedToken");
        
        assertThat(result).isFalse();
    }
    
    @Test
    void validateJwtToken_withExpiredToken_shouldReturnFalse() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 2 * jwtExpirationMs))
                .setExpiration(new Date(System.currentTimeMillis() - jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
        boolean result = jwtUtils.validateJwtToken(token);
        
        assertThat(result).isFalse();
    }
    
    @Test
    void validateJwtToken_withUnsupportedJwtToken_shouldReturnFalse() {
        String unsupportedAlgoHeader = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0"; // {"alg":"none","typ":"JWT"}
        String payload = "eyJzdWIiOiJ0ZXN0VXNlciJ9"; // {"sub":"testUser"}
        String signature = ""; // Pas de signature pour "none"
        String invalidToken = unsupportedAlgoHeader + "." + payload + "." + signature;
        
        boolean result = jwtUtils.validateJwtToken(invalidToken);
        
        assertThat(result).isFalse();
    }
    
    @Test
    void validateJwtToken_withEmptyClaims_shouldReturnFalse() {
        boolean result = jwtUtils.validateJwtToken("");
        
        assertThat(result).isFalse();
    }
    
    @Test
    void generateTokenFromUsername_shouldReturnValidToken() {
        String username = "testUser";
        
        String token = jwtUtils.generateTokenFromUsername(username);
    
        assertThat(token).isNotNull();
        
        String extractedUsername = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        assertThat(extractedUsername).isEqualTo(username);
    }
    
    // Méthode utilitaire pour créer un JWT de test valide
    private String createTestJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
