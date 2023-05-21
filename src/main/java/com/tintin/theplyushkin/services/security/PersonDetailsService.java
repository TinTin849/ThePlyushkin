package com.tintin.theplyushkin.services.security;

import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.repositories.UsersRepository;
import com.tintin.theplyushkin.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public PersonDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> person = usersRepository.findByUsername(username);
        if (person.isPresent() && person.get().getIsActive()) {
            return new PersonDetails(person.get());
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
