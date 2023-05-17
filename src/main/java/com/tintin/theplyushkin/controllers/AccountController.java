package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @RequestMapping()
    public String account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        User user = userDetails.getPerson();
        model.addAttribute("user", user);

        return "account/personal";
    }
}
