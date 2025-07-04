package com.fr.spring.groupwork.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * File TestController.java
 * This controller provides endpoints for testing access control based on user roles.
 * It includes methods that return different content based on the user's role.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyAuthority('OPTIONCLASS_STUDENT','TEAM_MEMBER','SUPERVISING_STAFF','COACHES','JURY_MEMBERS')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasAnyAuthority('SUPERVISING_STAFF','COACHES','JURY_MEMBERS')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAnyAuthority('ADMIN_SYSTEM','ADMIN_PROJECT','ADMIN_OPTION')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
