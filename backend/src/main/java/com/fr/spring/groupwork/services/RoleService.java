package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;
import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.models.enums.ERole;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> getRoleById(Integer id);
    Optional<Role> getRoleByName(ERole name);
}
