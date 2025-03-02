package com.genesisresources.controller;

import com.genesisresources.model.User;
import com.genesisresources.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            String response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id, @RequestParam(required = false) Boolean detail) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (Boolean.TRUE.equals(detail)) {
            return ResponseEntity.ok(user.get()); // Vrátí všechna pole
        } else {
            Map<String, Object> basicUser = new HashMap<>();
            basicUser.put("id", user.get().getId());
            basicUser.put("name", user.get().getName());
            basicUser.put("surname", user.get().getSurname());
            return ResponseEntity.ok(basicUser);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) Boolean detail) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        if (Boolean.TRUE.equals(detail)) {
            return ResponseEntity.ok(users);
        } else {
            List<Map<String, Object>> basicUsers = users.stream().map(user -> {
                Map<String, Object> basicUser = new HashMap<>();
                basicUser.put("id", user.getId());
                basicUser.put("name", user.getName());
                basicUser.put("surname", user.getSurname());
                return basicUser;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(basicUsers);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user.getName(), user.getSurname());
            return ResponseEntity.ok("Uživatel byl úspěšně aktualizován.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Uživatel byl úspěšně smazán.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
