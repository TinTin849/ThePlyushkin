package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.repositories.CollectionItemsRepository;
import com.tintin.theplyushkin.repositories.CollectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CollectionItemsService {
    private final CollectionItemsRepository collectionItemsRepository;

    @Autowired
    public CollectionItemsService(CollectionItemsRepository collectionItemsRepository) {
        this.collectionItemsRepository = collectionItemsRepository;
    }

    public CollectionItem findById(int id) {
        return collectionItemsRepository.findById(id).get();
    }

    @Transactional
    public CollectionItem save(CollectionItem collectionItem) {
        return collectionItemsRepository.save(collectionItem);
    }

    @Transactional
    public void delete(int id) {
        collectionItemsRepository.deleteById(id);
    }
}
