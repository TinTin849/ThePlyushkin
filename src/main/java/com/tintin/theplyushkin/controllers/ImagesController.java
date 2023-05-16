package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.ItemImage;
import com.tintin.theplyushkin.services.CollectionItemsService;
import com.tintin.theplyushkin.services.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void showImageOfCollection(@PathVariable("id") int id,
                                      HttpServletResponse response) throws IOException {
        var collection = collectionsService.findById(id);
        String image = collection.getImgUrl();

        var imgFile = System.getProperty("user.dir") + "/collection-photos/" + image;
        showImage(response, imgFile);
    }

    @GetMapping("/items/{id}")
    public void showItemOfCollection(@PathVariable("id") int id,
                                     HttpServletResponse response) throws IOException {
        var item = collectionItemsService.findById(id);
        List<ItemImage> images = item.getItemImages();
        String image = images.get(0).getImgUrl();

        var imgFile = System.getProperty("user.dir") + "/item-photos/" + image;
        showImage(response, imgFile);
    }

    private void showImage(HttpServletResponse response, String imgFile) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(imgFile));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] bytes = bos.toByteArray();

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(bytes);

        response.getOutputStream().close();
    }
}
