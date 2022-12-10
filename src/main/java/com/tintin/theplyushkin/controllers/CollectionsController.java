package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.models.TypeOfCollection;
import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.TypesOfCollectionService;
import com.tintin.theplyushkin.services.security.VisibilityLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/collections")
public class CollectionsController {
    public static final String DEFAULT_VISIBILITY_OF_COLLECTION = "PUBLIC";

    private final CollectionsService collectionsService;
    private final TypesOfCollectionService typesOfCollectionService;
    private final VisibilityLevelService visibilityLevelService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService,
                                 TypesOfCollectionService typesOfCollectionService,
                                 VisibilityLevelService visibilityLevelService) {
        this.collectionsService = collectionsService;
        this.typesOfCollectionService = typesOfCollectionService;
        this.visibilityLevelService = visibilityLevelService;
    }

    @RequestMapping()
    public String index() {
        return "collections/index";
    }

    @RequestMapping("/my")
    public String allUserCollections(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        List<Collection> userCollections = collectionsService.findByCreator(userDetails.getPerson());
        model.addAttribute("userCollections", userCollections);

        return "collections/all_user_collections";
    }

    @RequestMapping("/my/{id}")
    public String userCollection(@PathVariable("id") int id, Model model) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        //todo access system to collections by user

        Collection currentCollection = collectionsService.findById(id);
        List<CollectionItem> itemsOfCollection = currentCollection.getItemsOfCollection();
        model.addAttribute("itemsOfCollection", itemsOfCollection);
        model.addAttribute("collection", currentCollection);

        return "collections/collection";
    }

    @RequestMapping("/new")
    public String newCollection(Model model) {
        Collection collection = new Collection();

        model.addAttribute("typesOfCollection", typesOfCollectionService.findAll());
        model.addAttribute("collection", collection);

        return "collections/new_user_collection";
    }

    @RequestMapping("/add")
    public String addCollection(@ModelAttribute("newCollection") Collection collection, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        Person creator = userDetails.getPerson();

        collection.setId(null);

        collection.setTypeOfCollection(typesOfCollectionService.findById(collection.getTypeOfCollection().getId()));
        collection.setCreator(creator);
        collection.setVisibility(visibilityLevelService.findByName(DEFAULT_VISIBILITY_OF_COLLECTION));

        collectionsService.save(collection);

        return "redirect:/collections/my";
    }
}
