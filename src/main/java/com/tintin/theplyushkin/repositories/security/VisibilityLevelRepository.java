package com.tintin.theplyushkin.repositories.security;

import com.tintin.theplyushkin.models.security.VisibilityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisibilityLevelRepository extends JpaRepository<VisibilityLevel, Integer> {
    VisibilityLevel findFirstByLevelName(String name);
}
