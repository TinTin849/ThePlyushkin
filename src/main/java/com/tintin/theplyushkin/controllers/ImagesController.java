package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.repositories.CollectionsRepository;
import com.tintin.theplyushkin.services.security.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/images")
public class ImagesController {
    private final CollectionsService collectionsService;

    @Autowired
    public ImagesController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @GetMapping("/collections/{id}")
    public void showImageOfCollection(@PathVariable("id") int id
            , HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] imageInByte = collectionsService.getImageByCollectionId(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(imageInByte);

        response.getOutputStream().close();
    }

//    @GetMapping("/items/{id}")
//    public void showItemOfCollection(@PathVariable("id") int id
//            , HttpServletResponse response, HttpServletRequest request) throws IOException {
//        CollectionItem item = collectionItemsService.findById(id);
//        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//        response.getOutputStream().write(item.getImage());
//
//        response.getOutputStream().close();
//    }
}
