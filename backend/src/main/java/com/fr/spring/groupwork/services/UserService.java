package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;
import com.fr.spring.groupwork.models.User;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    User saveUser(User user);
    void deleteUser(Long id);
}
