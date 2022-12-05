package com.tintin.theplyushkin.services.security;

import com.tintin.theplyushkin.repositories.security.VisibilityLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisibilityLevelService {
    private final VisibilityLevelRepository visibilityLevelRepository;

    @Autowired
    public VisibilityLevelService(VisibilityLevelRepository visibilityLevelRepository) {
        this.visibilityLevelRepository = visibilityLevelRepository;
    }
}
