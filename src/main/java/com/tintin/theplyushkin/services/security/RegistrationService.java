package com.tintin.theplyushkin.services.security;

import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.repositories.security.AccessLevelRepository;
import com.tintin.theplyushkin.repositories.security.PeopleRepository;
import com.tintin.theplyushkin.repositories.security.VisibilityLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final AccessLevelRepository accessLevelRepository;
    private final VisibilityLevelRepository visibilityLevelRepository;

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AccessLevelRepository accessLevelRepository,
                               VisibilityLevelRepository visibilityLevelRepository,
                               PeopleRepository peopleRepository,
                               PasswordEncoder passwordEncoder) {
        this.accessLevelRepository = accessLevelRepository;
        this.visibilityLevelRepository = visibilityLevelRepository;
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(accessLevelRepository.findFirstByLevelName("ROLE_USER"));
        person.setVisibility(visibilityLevelRepository.findFirstByLevelName("PUBLIC"));

        peopleRepository.save(person);
    }
}
