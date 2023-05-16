package com.tintin.theplyushkin.util;

import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.services.security.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (peopleService.loadUserByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("name", "", "Человек с таким ником уже существует!");
        }
    }
}
