package com.fr.spring.groupwork.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File UserDetailsImpl.java
 * This class implements the UserDetails interface, providing user details for authentication.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;
  
  private ETypeUser typeUser;
  
  private boolean isActive;
  private Long classeId;
  private String classeName;

  public UserDetailsImpl(Long id, String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities, ETypeUser typeUser, boolean isActive, Long classeId, String classeName) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.typeUser = typeUser;
    this.isActive = isActive;
   this.classeId = classeId;
   this.classeName = classeName;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    Long classeId = user.getClasse() != null ? user.getClasse().getId() : null;
    String classeName = user.getClasse() != null ? user.getClasse().getName() : null;
    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities,
        user.getTypeUser(),
        user.isActive(),
        classeId,
        classeName);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
  
  public ETypeUser getTypeUser() {
    return typeUser;
  }
  
  public boolean isActive() {
    return isActive;
  }

  public Long getClasseId() {
    return classeId;
  }

  public String getClasseName() {
    return classeName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive;  // Maintenant basé sur la valeur de isActive
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
