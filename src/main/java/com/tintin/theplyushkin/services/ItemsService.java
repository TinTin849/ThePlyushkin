package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.Item;
import com.tintin.theplyushkin.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Item findById(int id) {
        return itemsRepository.findById(id).get();
    }

    @Transactional
    public Item save(Item item) {
        return itemsRepository.save(item);
    }



    @Transactional
    public void delete(int id) {
        itemsRepository.deleteById(id);
    }
}
