package com.genesisresources.service;

import com.genesisresources.model.User;
import com.genesisresources.reposity.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Vytvoření uživatele nebo aktualizace podle personId
    public String createUser(User user) {
        if (userRepository.personIdExists(user.getPersonId())) {
            userRepository.updateUserByPersonId(user);
            return "User updated successfully";
        }
        user.setUuid(UUID.randomUUID().toString());
        userRepository.createUser(user);
        return "User created successfully";
    }

    // Načtení uživatele podle ID s možností detailního zobrazení
    public User getUserById(Long id, boolean detail) {
        return detail ? userRepository.getUserByIdWithDetails(id) : userRepository.getUserById(id);
    }

    // Načtení všech uživatelů s možností detailního zobrazení
    public List<User> getAllUsers(boolean detail) {
        return detail ? userRepository.getAllUsersWithDetails() : userRepository.getAllUsers();
    }

    // Aktualizace uživatele (pouze jméno a příjmení)
    public boolean updateUser(User user) {
        return userRepository.updateUser(user) > 0;
    }

    // Smazání uživatele podle ID
    public boolean deleteUser(Long id) {
        return userRepository.deleteUser(id) > 0;
    }



    public void exportUsersToCSV(HttpServletResponse response) {
        try (CSVWriter writer = new CSVWriter(new PrintWriter(response.getWriter()))) {
            // Hlavička CSV
            String[] header = {"ID", "Name", "Surname", "Person ID", "UUID"};
            writer.writeNext(header);

            // Data
            List<User> users = getAllUsers(true);  // true = s detaily
            for (User user : users) {
                String[] data = {
                        String.valueOf(user.getId()),
                        user.getName(),
                        user.getSurname(),
                        user.getPersonId(),
                        user.getUuid()
                };
                writer.writeNext(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Import uživatelů z CSV
    public String importUsersFromCSV(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext();  // Přeskočení hlavičky

            while ((line = reader.readNext()) != null) {
                User user = new User();
                user.setName(line[1]);
                user.setSurname(line[2]);
                user.setPersonId(line[3]);
                user.setUuid(line[4]);

                createUser(user);
            }
            return "Import byl úspěšný!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Chyba při importu!";
        }
    }
}
