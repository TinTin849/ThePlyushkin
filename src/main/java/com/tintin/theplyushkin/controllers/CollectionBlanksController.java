package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionType;
import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.CollectionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.tintin.theplyushkin.controllers.CollectionsController.DEFAULT_VISIBILITY_OF_COLLECTION;

@Controller
@RequestMapping("/collection-blanks")
public class CollectionBlanksController {
    private final CollectionTypesService collectionTypesService;

    @Autowired
    public CollectionBlanksController(CollectionTypesService collectionTypesService) {
        this.collectionTypesService = collectionTypesService;
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
    public String newCollectionBlank(Model model) {
//        model.addAttribute("typesOfCollection", collectionTypesService.findAll());
//        model.addAttribute("collection", new Collection());
//
//        return "collections/new_user_collection";
        return null;
    }

    @PostMapping("/add")
    public String addCollectionBlank(Model model,
                                @RequestParam("image") MultipartFile multipartFile,
                                @ModelAttribute("newCollection") Collection collection) throws IOException {
//        User user = getCurrentUser();
//        String fileName = saveCollectionImage(multipartFile, collection);
//
//        collection.setImgUrl(fileName);
//        collection.setId(null);
//        collection.setCollectionType(
//                collectionTypesService.findById(
//                        collection.getCollectionType().getId()
//                )
//        );
//        collection.setLikes(0);
//        collection.setUser(user);
//        collection.setVisibility(DEFAULT_VISIBILITY_OF_COLLECTION);
//
//        Collection savedCollection = collectionsService.save(collection);
//        model.addAttribute("collectionId", savedCollection.getId());
//
//        return "redirect:/collections/my/" + savedCollection.getId();
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCollectionBlank(@PathVariable("id") int id) {
        System.out.println("test ok");

        var collectionBlank = collectionTypesService.findById(id);
        System.out.println(collectionBlank);
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