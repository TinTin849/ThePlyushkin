package com.tintin.theplyushkin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collections")
public class CollectionsController {

    @GetMapping()
    public String index() {
        return "collections/index";
    }

    @GetMapping("/my")
    public String userCollections() {
        return "collections/my_collection";
    }
}
