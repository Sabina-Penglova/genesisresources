package com.genesisresources.repository;

import com.genesisresources.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPersonId(rs.getString("person_id"));
        user.setUuid(rs.getString("uuid"));
        return user;
    };

    public void createUser(User user) {
        String sql = "INSERT INTO users (name, surname, person_id, uuid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getPersonId(), user.getUuid());
    }




    public User getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public boolean personIdExists(String personId) {
        String sql = "SELECT COUNT(*) FROM users WHERE person_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, personId);
        return count != null && count > 0;
    }

    public void updateUser(Long id, String name, String surname) {
        String sql = "UPDATE users SET name = ?, surname = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, surname, id);
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteAll() {
        String sql = "DELETE FROM users";
        jdbcTemplate.update(sql);
    }
}
