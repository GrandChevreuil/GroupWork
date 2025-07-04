package com.fr.spring.groupwork.security.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;

import com.fr.spring.groupwork.security.services.UserDetailsImpl;

/**
 * Interface pour JwtUtils
 * Cette interface permet de faciliter les mocks pour les tests
 * 
 * @author Mathis Mauprivez
 * @date 05/07/2025
 */
public interface IJwtUtils {
    /**
     * Extrait le JWT des cookies de la requête
     * 
     * @param request la requête HTTP
     * @return le JWT ou null s'il n'existe pas
     */
    String getJwtFromCookies(HttpServletRequest request);
    
    /**
     * Génère un cookie JWT pour l'utilisateur authentifié
     * 
     * @param userPrincipal les détails de l'utilisateur
     * @return le cookie JWT
     */
    ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal);
    
    /**
     * Crée un cookie vide pour nettoyer l'ancien JWT
     * 
     * @return le cookie JWT vide
     */
    ResponseCookie getCleanJwtCookie();
    
    /**
     * Extrait le nom d'utilisateur du JWT
     * 
     * @param token le token JWT
     * @return le nom d'utilisateur
     */
    String getUserNameFromJwtToken(String token);
    
    /**
     * Valide un token JWT
     * 
     * @param authToken le token à valider
     * @return true si le token est valide, false sinon
     */
    boolean validateJwtToken(String authToken);
    
    /**
     * Génère un token JWT à partir d'un nom d'utilisateur
     * 
     * @param username le nom d'utilisateur
     * @return le token JWT généré
     */
    String generateTokenFromUsername(String username);
}
