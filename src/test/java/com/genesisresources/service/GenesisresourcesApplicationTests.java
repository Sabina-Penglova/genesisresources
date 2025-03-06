package com.genesisresources.service;

import com.genesisresources.model.User;
import com.genesisresources.repository.JpaUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenesisresourcesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaUserRepository userRepository;

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
        User newUser = new User();
        newUser.setName("TestUser");
        newUser.setSurname("TestSurname");
        newUser.setPersonId("P004");
        newUser.setUuid(UUID.randomUUID().toString());

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Uživatel úspěšně vytvořen.");
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setPersonId("P001");
        user.setUuid(UUID.randomUUID().toString());
        userRepository.save(user);
        Long id = user.getId();

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(
                "/api/v1/users/" + id,
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
        User user1 = new User();
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setPersonId("P001");
        user1.setUuid(UUID.randomUUID().toString());
        User user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setPersonId("P002");
        user2.setUuid(UUID.randomUUID().toString());
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
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setPersonId("P001");
        user.setUuid(UUID.randomUUID().toString());
        userRepository.save(user);
        Long id = user.getId();

        user.setName("UpdatedJohn");
        user.setSurname("UpdatedDoe");

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users/" + id,
                HttpMethod.PUT,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Uživatel úspěšně aktualizován.");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setName("ToDelete");
        user.setSurname("User");
        user.setPersonId("P005");
        user.setUuid(UUID.randomUUID().toString());
        userRepository.save(user);
        Long id = user.getId();

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users/" + id,
                HttpMethod.DELETE,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Uživatel úspěšně smazán.");
    }

    @Test
    void testCreateUserWithDuplicatePersonId() {
        User existingUser = new User();
        existingUser.setName("Existující");
        existingUser.setSurname("Uživatel");
        existingUser.setPersonId("P007");
        existingUser.setUuid(UUID.randomUUID().toString());
        userRepository.save(existingUser);

        User newUser = new User();
        newUser.setName("Nový");
        newUser.setSurname("Uživatel");
        newUser.setPersonId("P007");
        newUser.setUuid(UUID.randomUUID().toString());

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/users",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Tento personID již existuje");
    }
}