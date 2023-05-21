package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.*;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.security.PersonDetails;
import com.tintin.theplyushkin.services.*;
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
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemsController {
    public static final VisibilityLevel DEFAULT_VISIBILITY_OF_ITEM = VisibilityLevel.PRIVATE;

    private final ItemsService itemsService;
    private final CollectionsService collectionsService;
    private final FeaturesService featuresService;
    private final ItemFeaturesService itemFeaturesService;
    private final ItemImagesService itemImagesService;

    @Autowired
    public ItemsController(ItemsService itemsService,
                           CollectionsService collectionsService,
                           FeaturesService featuresService,
                           ItemFeaturesService itemFeaturesService,
                           ItemImagesService itemImagesService) {
        this.itemsService = itemsService;
        this.collectionsService = collectionsService;
        this.featuresService = featuresService;
        this.itemFeaturesService = itemFeaturesService;
        this.itemImagesService = itemImagesService;
    }

    @RequestMapping("/{id}")
    public String collectionItem(Model model,
                                 @PathVariable("id") int id) {
        User currentUser = getCurrentUser();

        Item currentItem = itemsService.findById(id);

        boolean isOwner = false;
        var currentCollection = currentItem.getCollection();
        if (currentUser.getId() == currentCollection.getUser().getId()) {
            isOwner = true;
        } else {
            if (currentCollection.getVisibility() == VisibilityLevel.PRIVATE){
                if (currentItem.getVisibility() == VisibilityLevel.PRIVATE) {
                    return "auth/access-denied";
                }
            }
        }
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("currentItem", currentItem);

        List<ItemFeature> currentItemFeatures = currentItem.getFeaturesOfItem();
        model.addAttribute("currentItemFeatures", currentItemFeatures);

        return "items/collection_item";
    }

    @RequestMapping("/new/{id}")
    public String newItem(Model model,
                          @PathVariable("id") int collectionId) {
        model.addAttribute("collectionId", collectionId);
        model.addAttribute("collectionItem", new Item());

        return "items/new_collection_item";
    }

    @PostMapping("/new/{id}/features")
    public String newItemFeatures(Model model,
                                  @RequestParam("image") MultipartFile multipartFile,
                                  @ModelAttribute("collectionItem") Item item,
                                  @PathVariable("id") int collectionId) throws IOException {
        var currentCollection = collectionsService.findById(collectionId);

        item.setId(null);
        item.setCollection(currentCollection);
        item.setVisibility(DEFAULT_VISIBILITY_OF_ITEM);
        item.setLikes(0);
        item.setIsActive(true);

        var savedItem = itemsService.save(item);

        var fileName = saveItemImage(multipartFile, savedItem);
        var image = new ItemImage();
        image.setItem(savedItem);
        image.setImgUrl(fileName);
        image = itemImagesService.save(image);
        savedItem.setItemImages(List.of(image));

        model.addAttribute("collectionItem", savedItem);

        var featuresOfCollectionType = currentCollection
                .getCollectionType()
                .getFeaturesOfCollectionType();
        model.addAttribute("featuresOfCollectionType", featuresOfCollectionType);

        return "items/new_collection_item_features";
    }

    private static String saveItemImage(MultipartFile multipartFile,
                                        Item item) throws IOException {
        String fileName = UUID.randomUUID() + ".jpg";

        String uploadDir = "item-photos";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return fileName;
    }

    @RequestMapping("/add/{id}")
    public String addItem(Model model,
                          @ModelAttribute("newCollection") Collection collection,
                          @PathVariable("id") int collectionItem,
                          @RequestParam Map<String, String> allFeatures) {
        var currentCollectionItem = itemsService.findById(collectionItem);

        for (var entry : allFeatures.entrySet()) {
            if (!entry.getValue().isBlank() && entry.getKey().matches("\\d+")) {
                int featureId = Integer.parseInt(entry.getKey());
                var feature = featuresService.findById(featureId);

                var featureOfItem = new ItemFeature(currentCollectionItem, feature, entry.getValue());
                itemFeaturesService.save(featureOfItem);
            }
        }
        model.addAttribute("itemId", collectionItem);

        return "redirect:/items/" + currentCollectionItem.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable("id") int id) {
        int collectionId = itemsService
                .findById(id)
                .getCollection()
                .getId();
        itemsService.delete(id);

        return "redirect:/collections/my/" + collectionId;
    }

    private static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        return userDetails.getPerson();
    }
}
