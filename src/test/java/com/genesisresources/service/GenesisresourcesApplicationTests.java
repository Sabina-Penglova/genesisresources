package com.genesisresources.service;



import com.genesisresources.model.User;
import com.genesisresources.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenesisresourcesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password123");
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    void testCreateUser() {
        User newUser = new User("TestUser", "TestSurname", "P004");

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Uživatel byl úspěšně vytvořen.");
    }

    @Test
    void testGetUserById() {
        User user = new User("John", "Doe", "P001");
        userRepository.createUser(user); // Nebo userRepository.save(user);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(
                "/api/v1/users/1",
                HttpMethod.GET,
                request,
                User.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("John");
        assertThat(response.getBody().getSurname()).isEqualTo("Doe");
    }


    @Test
    void testGetAllUsers() {
        User user1 = new User("John", "Doe", "P001");
        User user2 = new User("Jane", "Smith", "P002");
        userRepository.save(user1);
        userRepository.save(user2);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(
                "/api/v1/users",
                HttpMethod.GET,
                request,
                User[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testUpdateUser() {
        User user = new User("John", "Doe", "P001");
        userRepository.save(user);

        user.setName("UpdatedJohn");
        user.setSurname("UpdatedDoe");

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users/1",
                HttpMethod.PUT,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Uživatel byl úspěšně aktualizován.");
    }

    @Test
    void testDeleteUser() {
        User user = new User("ToDelete", "User", "P005");
        userRepository.save(user);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users/1",
                HttpMethod.DELETE,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Uživatel byl úspěšně smazán.");
    }
}
