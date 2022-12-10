package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.models.Feature;
import com.tintin.theplyushkin.models.FeatureOfCollectionType;
import com.tintin.theplyushkin.models.FeatureOfItem;
import com.tintin.theplyushkin.models.TypeOfCollection;
import com.tintin.theplyushkin.services.CollectionItemsService;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.FeatureService;
import com.tintin.theplyushkin.services.FeaturesOfItemService;
import com.tintin.theplyushkin.services.security.VisibilityLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class CollectionItemsController {
    public static final String DEFAULT_VISIBILITY_OF_ITEM = "PUBLIC";

    private final CollectionItemsService collectionItemsService;
    private final CollectionsService collectionsService;
    private final VisibilityLevelService visibilityLevelService;
    private final FeatureService featureService;
    private final FeaturesOfItemService featuresOfItemService;

    @Autowired
    public CollectionItemsController(CollectionItemsService collectionItemsService,
                                     CollectionsService collectionsService,
                                     VisibilityLevelService visibilityLevelService,
                                     FeatureService featureService,
                                     FeaturesOfItemService featuresOfItemService) {
        this.collectionItemsService = collectionItemsService;
        this.collectionsService = collectionsService;
        this.visibilityLevelService = visibilityLevelService;
        this.featureService = featureService;
        this.featuresOfItemService = featuresOfItemService;
    }

    @RequestMapping("/{id}")
    public String collectionItem(@PathVariable("id") int id, Model model) {
        //todo access system to collections by user
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        CollectionItem currentItem = collectionItemsService.findById(id);
        model.addAttribute("currentItem", currentItem);

        List<FeatureOfItem> currentItemFeatures = currentItem.getFeaturesOfCollectionItem();
        model.addAttribute("currentItemFeatures", currentItemFeatures);

        return "items/collection_item";
    }

    @RequestMapping("/new/{id}")
    public String newItem(Model model,
                          @PathVariable("id") int collectionId) {
        model.addAttribute("collectionId", collectionId);

        CollectionItem collectionItem = new CollectionItem();
        model.addAttribute("collectionItem", collectionItem);

        return "items/new_collection_item";
    }

    @RequestMapping("/new/{id}/features")
    public String newItemFeatures(Model model,
                                  @ModelAttribute("collectionItem") CollectionItem collectionItem,
                                  @PathVariable("id") int collectionId) {
        Collection currentCollection = collectionsService.findById(collectionId);

        collectionItem.setCollection(currentCollection);
        collectionItem.setVisibility(visibilityLevelService.findByName(DEFAULT_VISIBILITY_OF_ITEM));
        collectionItem = collectionItemsService.save(collectionItem);

        model.addAttribute("collectionItem", collectionItem);

        TypeOfCollection currentTypeOfCollection = currentCollection.getTypeOfCollection();
        List<FeatureOfCollectionType> featuresOfCollectionType = currentTypeOfCollection.getFeaturesOfCollectionType();
        model.addAttribute("featuresOfCollectionType", featuresOfCollectionType);

        return "items/new_collection_item_features";
    }

    @RequestMapping("/add/{id}")
    public String addItem(Model model,
                          @ModelAttribute("newCollection") Collection collection,
                          @PathVariable("id") int collectionItem,
                          @RequestParam Map<String, String> allFeatures) {
        CollectionItem currentCollectionItem = collectionItemsService.findById(collectionItem);

        for (Map.Entry<String, String> entry : allFeatures.entrySet()) {
            if (!entry.getValue().isBlank()) {
                int featureId = Integer.parseInt(entry.getKey());
                Feature feature = featureService.findById(featureId);

                FeatureOfItem featureOfItem = new FeatureOfItem();
                featureOfItem.setFeature(feature);
                featureOfItem.setCollectionItem(currentCollectionItem);
                featureOfItem.setData(entry.getValue());

                featuresOfItemService.save(featureOfItem);
            }
        }

        int collectionId = currentCollectionItem.getCollection().getId();
        return "redirect:/collections/my/" + collectionId;
    }
}
