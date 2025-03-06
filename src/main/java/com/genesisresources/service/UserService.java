package com.genesisresources.service;

import com.genesisresources.model.User;
import com.genesisresources.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final JpaUserRepository userRepository;

    @Autowired
    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        if (user.getPersonId() == null || user.getPersonId().isBlank()) {
            throw new IllegalArgumentException("PersonID nesmí být prázdné.");
        }

        if (userRepository.existsByPersonId(user.getPersonId())) {
            throw new IllegalArgumentException("Tento personID již existuje: " + user.getPersonId());
        }

        if (user.getUuid() == null || user.getUuid().isEmpty()) {
            user.setUuid(UUID.randomUUID().toString());
        }

        userRepository.save(user);
        return "Uživatel úspěšně vytvořen.";
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, String name, String surname) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(name);
            user.setSurname(surname);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Uživatel s ID " + id + " nebyl nalezen.");
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Uživatel s ID " + id + " neexistuje.");
        }
    }
}