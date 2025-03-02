package com.genesisresources.repository;

import com.genesisresources.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    boolean existsByPersonId(String personId);
}
