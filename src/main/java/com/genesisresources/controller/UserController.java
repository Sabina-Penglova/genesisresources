package com.genesisresources.controller;

import com.genesisresources.model.User;
import com.genesisresources.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Vytvoření nového uživatele
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // 2. Načtení jednoho uživatele podle ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id,
                                            @RequestParam(value = "detail", required = false) Boolean detail) {
        User user = userService.getUserById(id, detail != null && detail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // 3. Načtení všech uživatelů
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(value = "detail", required = false) Boolean detail) {
        return ResponseEntity.ok(userService.getAllUsers(detail != null && detail));
    }

    // 4. Aktualizace uživatele
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user) ? ResponseEntity.ok("User updated") : ResponseEntity.notFound().build();
    }

    // 5. Smazání uživatele
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ? ResponseEntity.ok("User deleted") : ResponseEntity.notFound().build();
    }

    // 6. Export uživatelů do CSV
    @GetMapping("/export")
    public void exportUsersToCSV(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

        userService.exportUsersToCSV(response);
    }

    // 7. Import uživatelů z CSV
    @PostMapping("/import")
    public ResponseEntity<String> importUsersFromCSV(@RequestParam("file") MultipartFile file) {
        String message = userService.importUsersFromCSV(file);
        return ResponseEntity.ok(message);
    }
}
