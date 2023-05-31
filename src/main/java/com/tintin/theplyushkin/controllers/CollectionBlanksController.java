package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.*;
import com.tintin.theplyushkin.models.util.DataType;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.CollectionTypesService;
import com.tintin.theplyushkin.services.FeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection-blanks")
public class CollectionBlanksController {
    private final CollectionTypesService collectionTypesService;
    private final FeaturesService featuresService;

    @Autowired
    public CollectionBlanksController(CollectionTypesService collectionTypesService, FeaturesService featuresService) {
        this.collectionTypesService = collectionTypesService;
        this.featuresService = featuresService;
    }

    @RequestMapping()
    public String allUserCollectionBlanks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        User user = userDetails.getPerson();

        List<CollectionType> blanksOfUser = collectionTypesService.findByUser(user);
        List<CollectionType> activeBlanksOfUser = blanksOfUser.stream()
                .filter(it -> it.getVisibility() != VisibilityLevel.DELETED)
                .collect(Collectors.toList());
        model.addAttribute("blanksOfUser", activeBlanksOfUser);

        return "collection-blanks/all_user_collection_blanks";
    }

    @RequestMapping("/new")
    public String newCollectionBlank() {
        return "collection-blanks/new_collection_blank";
    }

    @PostMapping("/add")
    public String addItem(@RequestParam("blankName") String blankName,
                          @RequestParam Map<String, String> allFeatures) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        var user = userDetails.getPerson();

        var collectionType = new CollectionType();
        collectionType.setName(blankName);
        collectionType.setVisibility(VisibilityLevel.PUBLIC);
        collectionType.setUser(user);
        collectionType = collectionTypesService.save(collectionType);

        for (var entry : allFeatures.entrySet()) {
            if (!entry.getValue().isBlank()) {
                var feature = new Feature();
                feature.setName(entry.getValue());
                feature.setCollectionType(collectionType);

                try {
                    int num = Integer.parseInt(entry.getKey());
                    feature.setDataType(DataType.valueOf(allFeatures.get(num + "t")));
                    featuresService.save(feature);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return "redirect:/collection-blanks";
    }

    @DeleteMapping("/{id}")
    public String deleteCollectionBlank(@PathVariable("id") int id) {
        var collectionBlank = collectionTypesService.findById(id);
        collectionBlank.setVisibility(VisibilityLevel.DELETED);
        collectionTypesService.save(collectionBlank);

        return "redirect:/collection-blanks";
    }

//    @PostMapping("/{id}/visibility")
//    public String changeVisibility(@PathVariable("id") int id) {
//        var collection = collectionsService.findById(id);
//        if (collection.getVisibility() == VisibilityLevel.PRIVATE) {
//            collection.setVisibility(VisibilityLevel.PUBLIC);
//        } else {
//            collection.setVisibility(VisibilityLevel.PRIVATE);
//        }
//        collectionsService.save(collection);
//
//        return "redirect:/collections/my/" + collection.getId();
//    }
}