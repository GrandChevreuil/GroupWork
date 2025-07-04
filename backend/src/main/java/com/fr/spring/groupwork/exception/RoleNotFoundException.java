package com.fr.spring.groupwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * File : RoleNotFoundException.java
 * This exception is thrown when a requested role is not found in the system.
 * @author Mathis Mauprivez
 * @date 02/07/2025
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super("Error: Role '" + role + "' not found.");
    }
    public RoleNotFoundException() {
        super("Error: Role not found.");
    }
}
