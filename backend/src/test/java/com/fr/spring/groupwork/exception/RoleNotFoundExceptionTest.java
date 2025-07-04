package com.fr.spring.groupwork.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * File : RoleNotFoundExceptionTest.java
 * Tests unitaires pour la classe RoleNotFoundException
 * @author Mathis Mauprivez
 * @date 04/07/2025
 */

class RoleNotFoundExceptionTest {

    @Test
    void constructor_withRole_shouldSetCorrectMessage() {
        String roleName = "TEST_ROLE";
        RoleNotFoundException exception = new RoleNotFoundException(roleName);
        
        assertThat(exception.getMessage()).isEqualTo("Error: Role 'TEST_ROLE' not found.");
    }
    
    @Test
    void constructor_withoutRole_shouldSetDefaultMessage() {
        RoleNotFoundException exception = new RoleNotFoundException();
        
        assertThat(exception.getMessage()).isEqualTo("Error: Role not found.");
    }
    
    @Test
    void class_shouldHaveCorrectResponseStatus() {
        ResponseStatus annotation = RoleNotFoundException.class.getAnnotation(ResponseStatus.class);
        
        assertThat(annotation).isNotNull();
        assertThat(annotation.value()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
