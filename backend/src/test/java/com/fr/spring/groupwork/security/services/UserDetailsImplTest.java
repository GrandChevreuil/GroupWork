package com.fr.spring.groupwork.security.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Set;

import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.models.enums.ERole;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * File UserDetailsImplTest.java
 * Tests unitaires pour la classe UserDetailsImpl
 * @author Mathis Mauprivez
 * @date 03/07/2025
 */

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    private User user;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void init() {
        user = new User("u1", "u1@example.com", "pwd");
        user.setId(5L);
        user.setTypeUser(ETypeUser.STUDENT);
        user.setActive(true);
        user.setRoles(Set.of(new Role(ERole.OPTIONCLASS_STUDENT)));
        user.setClasse(new Classe());
        user.getClasse().setId(10L);
        user.getClasse().setName("CS_1");
    }

    @Test
    void build_shouldPopulateFields() {
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getId()).isEqualTo(5L);
        assertThat(userDetails.getUsername()).isEqualTo("u1");
        assertThat(userDetails.getEmail()).isEqualTo("u1@example.com");
        assertThat(userDetails.getTypeUser()).isEqualTo(ETypeUser.STUDENT);
        assertThat(userDetails.isActive()).isTrue();
        assertThat(userDetails.getClasseId()).isEqualTo(10L);
        assertThat(userDetails.getClasseName()).isEqualTo("CS_1");
        // Vérification de la présence de l'autorité
        assertThat(userDetails.getAuthorities())
            .extracting(ga -> ga.getAuthority())
            .contains("OPTIONCLASS_STUDENT");
    }

    @Test
    void getPassword_shouldReturnPassword() {
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getPassword()).isEqualTo("pwd");
    }

    @Test
    void accountStatusChecks_shouldAlwaysBeTrue() {
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabled_shouldReflectActiveAndStatus() {
        user.setActive(false);
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isEnabled()).isFalse();
        
        user.setActive(true);
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isEnabled()).isTrue();
    }
    
    @Test
    void isEnabled_shouldDependOnAllStatusMethods() {
        // Cas 1: isActive = false - Le reste ne devrait pas importer
        UserDetailsImpl testUser1 = new UserDetailsImpl(1L, "test", "test@example.com", "password", 
                Collections.emptyList(), ETypeUser.STUDENT, false) {
            @Override
            public boolean isAccountNonExpired() {
                return true; // même si c'est true, isEnabled devrait retourner false car isActive est false
            }
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
        };
        assertThat(testUser1.isEnabled()).isFalse();
        
        // Cas 2: isActive = true, mais isAccountNonExpired = false
        UserDetailsImpl testUser2 = new UserDetailsImpl(1L, "test", "test@example.com", "password",
                Collections.emptyList(), ETypeUser.STUDENT, true) {
            @Override
            public boolean isAccountNonExpired() {
                return false; // cette méthode retourne false
            }
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
        };
        assertThat(testUser2.isEnabled()).isFalse();
        
        // Cas 3: isActive = true, isAccountNonExpired = true, mais isAccountNonLocked = false
        UserDetailsImpl testUser3 = new UserDetailsImpl(1L, "test", "test@example.com", "password",
                Collections.emptyList(), ETypeUser.STUDENT, true) {
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                return false; // cette méthode retourne false
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
        };
        assertThat(testUser3.isEnabled()).isFalse();
        
        // Cas 4: isActive = true, isAccountNonExpired = true, isAccountNonLocked = true, mais isCredentialsNonExpired = false
        UserDetailsImpl testUser4 = new UserDetailsImpl(1L, "test", "test@example.com", "password",
                Collections.emptyList(), ETypeUser.STUDENT, true) {
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
                return false; // cette méthode retourne false
            }
        };
        assertThat(testUser4.isEnabled()).isFalse();
        
        // Cas 5: Toutes les méthodes retournent true
        UserDetailsImpl testUser5 = new UserDetailsImpl(1L, "test", "test@example.com", "password",
                Collections.emptyList(), ETypeUser.STUDENT, true) {
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
        };
        assertThat(testUser5.isEnabled()).isTrue();
    }

    @Test
    void equalsAndHashCode_basedOnId() {
        userDetails = UserDetailsImpl.build(user);
        User other = new User("u1", "u1@example.com", "pwd");
        other.setId(5L);
        UserDetailsImpl details2 = UserDetailsImpl.build(other);
        assertThat(userDetails)
            .isEqualTo(details2)
            .hasSameHashCodeAs(details2);

        other.setId(6L);
        UserDetailsImpl details3 = UserDetailsImpl.build(other);
        assertThat(userDetails).isNotEqualTo(details3);
    }

    @Test
    void equals_and_hashCode_shouldHandleNullAndDifferentType() {
        userDetails = UserDetailsImpl.build(user);
        // same reference
        assertThat(userDetails.equals(userDetails)).isTrue();
        // null
        assertThat(userDetails.equals(null)).isFalse();
        // different class
        assertThat(userDetails.equals("some string")).isFalse();
    }

    @Test
    void build_whenNoClasse_shouldHaveNullClasseFields() {
        user.setClasse(null);
        userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getClasseId()).isNull();
        assertThat(userDetails.getClasseName()).isNull();
    }
}
