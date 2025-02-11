package com.genesisresources.service;

import com.genesisresources.model.User;
import com.genesisresources.reposity.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);  // Mockování repository
        userService = new UserService(userRepository);
    }

    @Test
    void createUser_NewUser_ShouldReturnSuccessMessage() {
        User user = new User();  // Používáme správnou třídu User
        user.setName("Jack");
        user.setSurname("Sparrow");
        user.setPersonId("jXa4g3H7oPq2");

        when(userRepository.personIdExists("jXa4g3H7oPq2")).thenReturn(false);

        String result = userService.createUser(user);

        assertEquals("Uživatel byl úspěšně vytvořen.", result);
        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Jack");

        when(userRepository.getUserById(1L)).thenReturn(user);

        User result = userService.getUserById(1L, false);

        assertNotNull(result);
        assertEquals("Jack", result.getName());
    }

    @Test
    void deleteUser_ShouldReturnTrue_WhenUserExists() {
        when(userRepository.deleteUser(1L)).thenReturn(1);  // Simulace úspěšného smazání

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
    }

    @Test
    void deleteUser_ShouldReturnFalse_WhenUserDoesNotExist() {
        when(userRepository.deleteUser(999L)).thenReturn(0);  // Simulace neexistujícího uživatele

        boolean result = userService.deleteUser(999L);

        assertFalse(result);
    }
}
