package com.fr.spring.groupwork.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fr.spring.groupwork.models.enums.ERole;
import com.fr.spring.groupwork.models.Role;
import com.fr.spring.groupwork.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (ERole eRole : ERole.values()) {
            roleRepository.findByName(eRole)
                          .orElseGet(() -> roleRepository.save(new Role(eRole)));
        }
    }
}
