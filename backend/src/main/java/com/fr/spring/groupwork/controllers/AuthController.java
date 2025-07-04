package com.fr.spring.groupwork.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fr.spring.groupwork.models.*;
import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.payload.request.*;
import com.fr.spring.groupwork.payload.response.*;
import com.fr.spring.groupwork.repository.*;
import com.fr.spring.groupwork.security.jwt.IJwtUtils;
import com.fr.spring.groupwork.security.services.UserDetailsImpl;

/**
 * File AuthController.java
 * This class handles authentication requests, including user sign-in and sign-up.
 * It uses Spring Security for authentication and JWT for session management.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final String ROLE_NOT_FOUND_MESSAGE = "Error: Role not found.";
  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  private final IJwtUtils jwtUtils;
  
  
  @Autowired
  public AuthController(
      
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder encoder,
      IJwtUtils jwtUtils
      ) {
    
      this.authenticationManager= authenticationManager;
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.encoder= encoder;
      this.jwtUtils= jwtUtils;
  }
  
  

  @PostMapping("/signin")
  public ResponseEntity<UserInfoResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    // Construction via setters pour éviter constructeur trop large
    UserInfoResponse userInfo = new UserInfoResponse();
    userInfo.setId(userDetails.getId());
    userInfo.setUsername(userDetails.getUsername());
    userInfo.setEmail(userDetails.getEmail());
    userInfo.setRoles(roles);
    userInfo.setTypeUser(userDetails.getTypeUser());
    userInfo.setActive(userDetails.isActive());
    userInfo.setClasseId(userDetails.getClasseId());
    userInfo.setClasseName(userDetails.getClasseName());
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(userInfo);
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      boolean usernameExists = userRepository.existsByUsername(signUpRequest.getUsername());
      if (usernameExists) {
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
      }
      boolean emailExists = userRepository.existsByEmail(signUpRequest.getEmail());
      if (emailExists) {
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
      }

    // Création de l'utilisateur avec le typeUser si spécifié, sinon STUDENT par défaut
    User user;
    if (signUpRequest.getTypeUser() != null) {
      user = new User(
        signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()),
        signUpRequest.getTypeUser()
      );
    } else {
      user = new User(
        signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()),
        com.fr.spring.groupwork.models.enums.ETypeUser.STUDENT
      );
    }

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role defaultRole = roleRepository.findByName(ERole.OPTIONCLASS_STUDENT)
        .orElseThrow(com.fr.spring.groupwork.exception.RoleNotFoundException::new);
      roles.add(defaultRole);
    } else {
      strRoles.forEach(r -> {
        switch (r) {
          case "admin_system":
            roles.add(roleRepository.findByName(ERole.ADMIN_SYSTEM)
              .orElseThrow(() -> new com.fr.spring.groupwork.exception.RoleNotFoundException("admin_system")));
            break;
          case "admin_project":
            roles.add(roleRepository.findByName(ERole.ADMIN_PROJECT)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "admin_option":
            roles.add(roleRepository.findByName(ERole.ADMIN_OPTION)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "supervising_staff":
            roles.add(roleRepository.findByName(ERole.SUPERVISING_STAFF)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "optionclass_student":
            roles.add(roleRepository.findByName(ERole.OPTIONCLASS_STUDENT)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "team_member":
            roles.add(roleRepository.findByName(ERole.TEAM_MEMBER)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "coaches":
            roles.add(roleRepository.findByName(ERole.COACHES)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          case "jury_members":
            roles.add(roleRepository.findByName(ERole.JURY_MEMBERS)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_MESSAGE)));
            break;
          default:
            throw new com.fr.spring.groupwork.exception.RoleNotFoundException(r);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<MessageResponse> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
