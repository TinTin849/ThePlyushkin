package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.Item;
import com.tintin.theplyushkin.models.ItemFeature;
import com.tintin.theplyushkin.models.ItemImage;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import com.tintin.theplyushkin.services.*;
import com.tintin.theplyushkin.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CollectionItemsService collectionItemsService;
    private final CollectionsService collectionsService;
    private final FeatureService featureService;
    private final FeaturesOfItemService featuresOfItemService;
    private final ItemImageService itemImageService;

    @Autowired
    public ItemsController(CollectionItemsService collectionItemsService,
                           CollectionsService collectionsService,
                           FeatureService featureService,
                           FeaturesOfItemService featuresOfItemService,
                           ItemImageService itemImageService) {
        this.collectionItemsService = collectionItemsService;
        this.collectionsService = collectionsService;
        this.featureService = featureService;
        this.featuresOfItemService = featuresOfItemService;
        this.itemImageService = itemImageService;
    }

    @RequestMapping("/{id}")
    public String collectionItem(Model model,
                                 @PathVariable("id") int id) {
        //todo access system to collections by user
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        Item currentItem = collectionItemsService.findById(id);
        model.addAttribute("currentItem", currentItem);

        List<ItemFeature> currentItemFeatures = currentItem.getFeaturesOfItem();
        model.addAttribute("currentItemFeatures", currentItemFeatures);

        return "items/collection_item";
    }

    @RequestMapping("/new/{id}")
    public String newItem(Model model,
                          @PathVariable("id") int collectionId) {
        model.addAttribute("collectionId", collectionId);

        Item item = new Item();
        model.addAttribute("collectionItem", item);

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
        item.setVisibility(VisibilityLevel.PRIVATE);
        item.setLikes(0);
        item.setIsActive(true);

        var savedItem = collectionItemsService.save(item);

        var fileName = saveItemImage(multipartFile, item);
        var image = new ItemImage();
        image.setItem(savedItem);
        image.setImgUrl(fileName);
        image = itemImageService.save(image);
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
        var currentCollectionItem = collectionItemsService.findById(collectionItem);

        for (var entry : allFeatures.entrySet()) {
            if (!entry.getValue().isBlank() && entry.getKey().matches("\\d+")) {
                int featureId = Integer.parseInt(entry.getKey());
                var feature = featureService.findById(featureId);

                var featureOfItem = new ItemFeature(currentCollectionItem, feature, entry.getValue());
                featuresOfItemService.save(featureOfItem);
            }
        }
        model.addAttribute("itemId", collectionItem);

        return "redirect:/items/" + currentCollectionItem.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable("id") int id) {
        int collectionId = collectionItemsService
                .findById(id)
                .getCollection()
                .getId();
        collectionItemsService.delete(id);

        return "redirect:/collections/my/" + collectionId;
    }
}
