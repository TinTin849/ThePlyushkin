package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.security.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/collections")
public class CollectionsController {

    private final CollectionsService collectionsService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @GetMapping()
    public String index() {
        return "collections/index";
    }

    @GetMapping("/my")
    public String userCollections(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        List<Collection> userCollections = collectionsService.findByCreator(userDetails.getPerson());
        model.addAttribute("userCollections", userCollections);

        return "collections/my_collection";
    }
}
