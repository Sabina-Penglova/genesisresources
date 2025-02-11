package com.genesisresources.reposity;

import com.genesisresources.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO users (name, surname, person_id, uuid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getPersonId(), user.getUuid());
    }

    public void updateUserByPersonId(User user) {
        String sql = "UPDATE users SET name = ?, surname = ? WHERE person_id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getPersonId());
    }

    public User getUserById(Long id) {
        try {
            String sql = "SELECT id, name, surname FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, userBasicRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getUserByIdWithDetails(Long id) {
        try {
            String sql = "SELECT id, name, surname, person_id, uuid FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, userDetailedRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, surname FROM users";
        return jdbcTemplate.query(sql, userBasicRowMapper());
    }

    public List<User> getAllUsersWithDetails() {
        String sql = "SELECT id, name, surname, person_id, uuid FROM users";
        return jdbcTemplate.query(sql, userDetailedRowMapper());
    }

    public int updateUser(User user) {
        String sql = "UPDATE users SET name = ?, surname = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getId());
    }

    public int deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public boolean personIdExists(String personId) {
        String sql = "SELECT COUNT(*) FROM users WHERE person_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, personId);
        return count != null && count > 0;
    }
    // Hledání uživatelů podle jména nebo příjmení (case-insensitive)
    public List<User> searchUsers(String name, String surname) {
        String sql = "SELECT id, name, surname, person_id, uuid FROM users WHERE " +
                "(LOWER(name) LIKE LOWER(?) OR ? IS NULL) AND " +
                "(LOWER(surname) LIKE LOWER(?) OR ? IS NULL)";

        String namePattern = name != null ? "%" + name + "%" : null;
        String surnamePattern = surname != null ? "%" + surname + "%" : null;

        return jdbcTemplate.query(sql, userDetailedRowMapper(), namePattern, namePattern, surnamePattern, surnamePattern);
    }
    // Načtení uživatelů s možností stránkování (limit + offset)
    public List<User> getAllUsersWithPagination(int limit, int offset) {
        String sql = "SELECT id, name, surname, person_id, uuid FROM users LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, userDetailedRowMapper(), limit, offset);
    }



    private RowMapper<User> userBasicRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            return user;
        };
    }

    private RowMapper<User> userDetailedRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setPersonId(rs.getString("person_id"));
            user.setUuid(rs.getString("uuid"));
            return user;
        };
    }
}
