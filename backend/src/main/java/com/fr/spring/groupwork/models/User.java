package com.fr.spring.groupwork.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.ManyToOne;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File User.java
 * This class represents a user entity in the system.
 * It includes fields for username, email, password, roles, user type, and active status.
 * The class is annotated with JPA annotations for persistence and validation annotations for data integrity.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
@JsonIgnoreProperties({"students"})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classe_id")
  private Classe classe;

  @Enumerated(EnumType.STRING)
  private ETypeUser typeUser;

  private boolean isActive = true;

  public User() {
  }
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isActive = true;
    this.typeUser = ETypeUser.STUDENT;
  }

  public User(String username, String email, String password, ETypeUser typeUser) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.typeUser = typeUser;
    this.isActive = true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public ETypeUser getTypeUser() {
    return typeUser;
  }

  public void setTypeUser(ETypeUser typeUser) {
    this.typeUser = typeUser;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Classe getClasse() {
    return classe;
  }

  public void setClasse(Classe classe) {
    this.classe = classe;
  }
}
