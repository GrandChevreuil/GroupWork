package com.fr.spring.groupwork.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fr.spring.groupwork.models.User;
import com.fr.spring.groupwork.models.enums.ETypeUser;
import com.fr.spring.groupwork.repository.UserRepository;

/**
 * File UserServiceImpl.java
 * This class implements the UserService interface, providing methods for user-related operations.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByTypeUser(ETypeUser typeUser) {
        return userRepository.findByTypeUser(typeUser);
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findByIsActive(true);
    }

    @Override
    public List<User> getInactiveUsers() {
        return userRepository.findByIsActive(false);
    }

    @Override
    public User updateUserTypeUser(Long id, ETypeUser typeUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setTypeUser(typeUser);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User updateUserStatus(Long id, boolean isActive) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(isActive);
            return userRepository.save(user);
        }
        return null;
    }
}
