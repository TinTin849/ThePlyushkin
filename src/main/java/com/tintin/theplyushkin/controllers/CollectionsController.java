package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.Item;
import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.CollectionTypesService;
import com.tintin.theplyushkin.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/collections")
public class CollectionsController {
    public static final VisibilityLevel DEFAULT_VISIBILITY_OF_COLLECTION = VisibilityLevel.PRIVATE;

    private final CollectionsService collectionsService;
    private final CollectionTypesService collectionTypesService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService,
                                 CollectionTypesService collectionTypesService) {
        this.collectionsService = collectionsService;
        this.collectionTypesService = collectionTypesService;
    }

    @RequestMapping()
    public String index(Model model) {
        List<Collection> publicCollections = collectionsService.findPublic();
        model.addAttribute("publicCollections", publicCollections);

        return "collections/index";
    }

    @RequestMapping("/my")
    public String allUserCollections(Model model) {
        User currentUser = getCurrentUser();

        List<Collection> userCollections = collectionsService.findByUser(currentUser);
        model.addAttribute("userCollections", userCollections);

        return "collections/all_user_collections";
    }

    @RequestMapping("/my/{id}")
    public String userCollection(Model model,
                                 @PathVariable("id") int id) {
        User currentUser = getCurrentUser();

        Collection currentCollection = collectionsService.findById(id);

        boolean isOwner = false;
        if (currentUser.getId() == currentCollection.getUser().getId()) {
            isOwner = true;
        } else {
            if (currentCollection.getVisibility() == VisibilityLevel.PRIVATE) {
                return "auth/access-denied";
            }
        }
        model.addAttribute("isOwner", isOwner);

        List<Item> itemsOfCollection = currentCollection.getItems();
        model.addAttribute("itemsOfCollection", itemsOfCollection);
        model.addAttribute("collection", currentCollection);

        return "collections/collection";
    }

    @RequestMapping("/new")
    public String newCollection(Model model) {
        model.addAttribute("typesOfCollection", collectionTypesService.findAll());
        model.addAttribute("collection", new Collection());

        return "collections/new_user_collection";
    }

    @PostMapping("/add")
    public String addCollection(Model model,
                                @RequestParam("image") MultipartFile multipartFile,
                                @ModelAttribute("newCollection") Collection collection) throws IOException {
        User user = getCurrentUser();
        String fileName = saveCollectionImage(multipartFile, collection);

        collection.setImgUrl(fileName);
        collection.setId(null);
        collection.setCollectionType(
                collectionTypesService.findById(
                        collection.getCollectionType().getId()
                )
        );
        collection.setLikes(0);
        collection.setUser(user);
        collection.setVisibility(DEFAULT_VISIBILITY_OF_COLLECTION);

        Collection savedCollection = collectionsService.save(collection);
        model.addAttribute("collectionId", savedCollection.getId());

        return "redirect:/collections/my/" + savedCollection.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable("id") int id) {
        collectionsService.deleteById(id);

        return "redirect:/collections/my";
    }

    @PostMapping("/{id}/visibility")
    public String changeVisibility(@PathVariable("id") int id) {
        var collection = collectionsService.findById(id);
        if (collection.getVisibility() == VisibilityLevel.PRIVATE) {
            collection.setVisibility(VisibilityLevel.PUBLIC);
        } else {
            collection.setVisibility(VisibilityLevel.PRIVATE);
        }
        collectionsService.save(collection);

        return "redirect:/collections/my/" + collection.getId();
    }

    private static String saveCollectionImage(MultipartFile multipartFile,
                                              Collection collection) throws IOException {
        String fileName = UUID.randomUUID() + ".jpg";

        String uploadDir = "collection-photos";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return fileName;
    }

    private static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        return userDetails.getPerson();
    }
}
