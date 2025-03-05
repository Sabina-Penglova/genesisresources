package com.genesisresources.service;

import com.genesisresources.model.User;
import com.genesisresources.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setPersonId("P001");
    }

    @Test
    void testCreateUser() {
        when(userRepository.existsByPersonId("P001")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.createUser(user);

        assertThat(result).isEqualTo("User created successfully.");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John");
        assertThat(foundUser.get().getSurname()).isEqualTo("Doe");
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(1L, "UpdatedJohn", "UpdatedDoe");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}