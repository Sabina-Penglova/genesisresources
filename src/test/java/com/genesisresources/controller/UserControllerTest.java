package com.genesisresources.controller; // Ujistěte se, že zde je správný package

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesisresources.model.User;
import com.genesisresources.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser; // Opravený import
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "password123")  // Přidání mock uživatele pro autorizaci
    void createUser_ShouldReturnSuccessMessage() throws Exception {
        User user = new User();
        user.setName("Elizabeth");
        user.setSurname("Swann");
        user.setPersonId("yB9fR6tK0wLm");

        when(userService.createUser(user)).thenReturn("Uživatel byl úspěšně vytvořen.");

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())  // Očekáváme 200 OK
                .andExpect(jsonPath("$.message").value("Uživatel byl úspěšně vytvořen."));
    }
    @WithMockUser(username = "admin", password = "password123", roles = "ADMIN")


    @Test
    @WithMockUser(username = "admin", password = "password123")
    void createUser_ShouldReturnSuccessMessage() throws Exception {
        User user = new User();
        user.setName("Elizabeth");
        user.setSurname("Swann");
        user.setPersonId("yB9fR6tK0wLm");

        when(userService.createUser(user)).thenReturn("Uživatel byl úspěšně vytvořen.");

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Uživatel byl úspěšně vytvořen."));
    }

    @Test
    void unauthorizedAccess_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isUnauthorized());
    }
}
