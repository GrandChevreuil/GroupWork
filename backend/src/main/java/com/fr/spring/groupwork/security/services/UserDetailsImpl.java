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

  // Constructeur principal sans les attributs de classe (9 params réduit à 7)
  public UserDetailsImpl(Long id, String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities, ETypeUser typeUser, boolean isActive) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.typeUser = typeUser;
    this.isActive = isActive;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    Long classeId = user.getClasse() != null ? user.getClasse().getId() : null;
    String classeName = user.getClasse() != null ? user.getClasse().getName() : null;
    UserDetailsImpl details = new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities,
        user.getTypeUser(),
        user.isActive());
    // Attributs de classe assignés via setters
    details.setClasseId(classeId);
    details.setClasseName(classeName);
    return details;
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
  public void setClasseId(Long classeId) {
    this.classeId = classeId;
  }
  public void setClasseName(String classeName) {
    this.classeName = classeName;
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
    // L'utilisateur est “enabled” si le compte est actif, non expiré, non verrouillé et credentials valides
    return isActive
        && isAccountNonExpired()
        && isAccountNonLocked()
        && isCredentialsNonExpired();
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

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
