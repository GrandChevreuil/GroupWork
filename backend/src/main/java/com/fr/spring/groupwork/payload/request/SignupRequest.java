package com.fr.spring.groupwork.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File SignupRequest.java
 * This class represents the request payload for user signup.
 * It includes validation annotations to ensure the data meets certain criteria.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    private ETypeUser typeUser;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
    
    public ETypeUser getTypeUser() {
        return this.typeUser;
    }
    
    public void setTypeUser(ETypeUser typeUser) {
        this.typeUser = typeUser;
    }
}
