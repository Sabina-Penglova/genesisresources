package service;

import com.genesisresources.model.User;
import com.genesisresources.repository.UserRepository;
import com.genesisresources.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
        doNothing().when(userRepository).createUser(any(User.class));

        String result = userService.createUser(user);

        assertThat(result).isEqualTo("Uživatel byl úspěšně vytvořen.");
        verify(userRepository, times(1)).createUser(any(User.class));
    }


    @Test
    void testGetUserById() {
        when(userRepository.getUserById(1L)).thenReturn(user);

        User foundUser = userService.getUserById(1L);

        assertThat(foundUser.getName()).isEqualTo("John");
        assertThat(foundUser.getSurname()).isEqualTo("Doe");
    }

    @Test
    void testUpdateUser() {
        doNothing().when(userRepository).updateUser(anyLong(), anyString(), anyString());

        userService.updateUser(1L, "UpdatedJohn", "UpdatedDoe");

        verify(userRepository, times(1)).updateUser(1L, "UpdatedJohn", "UpdatedDoe");
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteUser(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteUser(1L);
    }
}
