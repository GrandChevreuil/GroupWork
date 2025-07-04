package com.fr.spring.groupwork.models;

import javax.persistence.*;
import com.fr.spring.groupwork.models.enums.ERole;

/**
 * File Role.java
 * This class represents a role entity in the system.
 * It includes an ID and a name, which is an enumeration of ERole.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
}