package com.tintin.theplyushkin.services.security;

import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.models.util.AccessLevel;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository,
                               PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(AccessLevel.USER);
        user.setVisibility(VisibilityLevel.PRIVATE);

        usersRepository.save(user);
    }
}