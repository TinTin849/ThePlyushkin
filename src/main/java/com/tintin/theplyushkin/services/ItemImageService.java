package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.ItemImage;
import com.tintin.theplyushkin.repositories.ItemImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    @Autowired
    public ItemImageService(ItemImageRepository itemImageRepository) {
        this.itemImageRepository = itemImageRepository;
    }

    @Transactional
    public ItemImage save(ItemImage itemImage) {
        return itemImageRepository.save(itemImage);
    }
}
