package com.tintin.theplyushkin.services.security;

import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {

    private final UsersRepository usersRepository;

    @Autowired
    public PeopleService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> loadUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
