package com.tintin.theplyushkin.controllers;

import com.tintin.theplyushkin.models.ItemImage;
import com.tintin.theplyushkin.services.CollectionsService;
import com.tintin.theplyushkin.services.ItemImagesService;
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
    private final ItemImagesService itemImagesService;

    @Autowired
    public ImagesController(CollectionsService collectionsService,
                            ItemImagesService itemImagesService) {
        this.collectionsService = collectionsService;
        this.itemImagesService = itemImagesService;
    }

    @GetMapping("/collections/{id}")
    public void showImageOfCollection(@PathVariable("id") int id,
                                      HttpServletResponse response) throws IOException {
        var collection = collectionsService.findById(id);
        String image = collection.getImgUrl();

        var imgFile = System.getProperty("user.dir") + "/collection-photos/" + image;
        showImage(imgFile, response);
    }

    @GetMapping("/items/{id}")
    public void showImageOfItem(@PathVariable("id") int id,
                                HttpServletResponse response) throws IOException {
        List<ItemImage> images = itemImagesService.findByItemId(id);
        //TODO отображение нескольких картинок стоит добавить
        String image = images.get(0).getImgUrl();

        var imgFile = System.getProperty("user.dir") + "/item-photos/" + image;
        showImage(imgFile, response);
    }

    private void showImage(String imgFile,
                           HttpServletResponse response) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(imgFile));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] bytes = bos.toByteArray();

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(bytes);

        response.getOutputStream().close();
    }
}
