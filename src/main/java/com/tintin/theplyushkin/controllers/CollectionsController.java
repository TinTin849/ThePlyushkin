package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.Item;
import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.TypesOfCollectionService;
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
    private final TypesOfCollectionService typesOfCollectionService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService,
                                 TypesOfCollectionService typesOfCollectionService) {
        this.collectionsService = collectionsService;
        this.typesOfCollectionService = typesOfCollectionService;
    }

    @RequestMapping()
    public String index() {
        return "collections/index";
    }

    @RequestMapping("/my")
    public String allUserCollections(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        List<Collection> userCollections = collectionsService.findByUser(
                userDetails.getPerson()
        );
        model.addAttribute("userCollections", userCollections);

        return "collections/all_user_collections";
    }

    @RequestMapping("/my/{id}")
    public String userCollection(@PathVariable("id") int id,
                                 Model model) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        //todo access system to collections by user

        Collection currentCollection = collectionsService.findById(id);
        List<Item> itemsOfCollection = currentCollection.getItems();
        model.addAttribute("itemsOfCollection", itemsOfCollection);
        model.addAttribute("collection", currentCollection);

        return "collections/collection";
    }

    @RequestMapping("/new")
    public String newCollection(Model model) {
        model.addAttribute("typesOfCollection", typesOfCollectionService.findAll());
        model.addAttribute("collection", new Collection());

        return "collections/new_user_collection";
    }

    @PostMapping("/add")
    public String addCollection(@RequestParam("image") MultipartFile multipartFile,
                                @ModelAttribute("newCollection") Collection collection,
                                Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        User user = userDetails.getPerson();

        var fileName = saveCollectionImage(multipartFile, collection);
        collection.setImgUrl(fileName);
        collection.setId(null);
        collection.setCollectionType(
                typesOfCollectionService.findById(
                        collection.getCollectionType().getId()
                )
        );
        collection.setLikes(0);
        collection.setUser(user);
        collection.setVisibility(DEFAULT_VISIBILITY_OF_COLLECTION);

        var savedCollection = collectionsService.save(collection);
        model.addAttribute("collectionId", savedCollection.getId());

        return "redirect:/collections/my/" + savedCollection.getId();
    }

    private static String saveCollectionImage(MultipartFile multipartFile,
                                              Collection collection) throws IOException {
        String fileName = UUID.randomUUID() + ".jpg";

        String uploadDir = "collection-photos";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return fileName;
    }

    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable("id") int id) {
        collectionsService.deleteById(id);

        return "redirect:/collections/my";
    }
}
