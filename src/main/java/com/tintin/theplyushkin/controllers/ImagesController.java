package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.CollectionItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Controller
@RequestMapping("/images")
public class ImagesController {
    private final CollectionsService collectionsService;
    private final CollectionItemsService collectionItemsService;

    @Autowired
    public ImagesController(CollectionsService collectionsService, CollectionItemsService collectionItemsService) {
        this.collectionsService = collectionsService;
        this.collectionItemsService = collectionItemsService;
    }

    @GetMapping("/collections/{id}")
    public void showImageOfCollection(@PathVariable("id") int id
            , HttpServletResponse response, HttpServletRequest request) throws IOException {
        Collection collection = collectionsService.findById(id);

        byte[] image = collection.getImage();
        if (image == null || image.length == 0) {
            File file = ResourceUtils.getFile("classpath:static/images/no_image.jpg");
            image = Files.readAllBytes(file.toPath());
        }

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image);

        response.getOutputStream().close();
    }

    @GetMapping("/items/{id}")
    public void showItemOfCollection(@PathVariable("id") int id
            , HttpServletResponse response, HttpServletRequest request) throws IOException {
        CollectionItem item = collectionItemsService.findById(id);

        byte[] image = item.getImage();
        if (image == null || image.length == 0) {
            File file = ResourceUtils.getFile("classpath:static/images/no_image.jpg");
            image = Files.readAllBytes(file.toPath());
        }

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image);

        response.getOutputStream().close();
    }

    @PostMapping("/collections/{id}/upload")
    public String uploadCollectionImage(@PathVariable("id") int id,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            //attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // save the file
        try {
            Collection currentCollection = collectionsService.findById(id);
            currentCollection.setImage(file.getInputStream().readAllBytes());
            collectionsService.save(currentCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        //attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/collections/my/" + id;
    }

    @PostMapping("/items/{id}/upload")
    public String uploadItemImage(@PathVariable("id") int id,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            //attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // save the file
        try {
            CollectionItem currentCollectionItem = collectionItemsService.findById(id);
            currentCollectionItem.setImage(file.getInputStream().readAllBytes());
            collectionItemsService.save(currentCollectionItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        //attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/items/" + id;
    }
}
