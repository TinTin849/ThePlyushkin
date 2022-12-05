package com.tintin.theplyushkin.repositories.security;

import com.tintin.theplyushkin.models.security.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLevelRepository extends JpaRepository<AccessLevel, Integer> {
    AccessLevel findFirstByLevelName(String name);
}
