package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.CollectionItem;
import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.repositories.CollectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CollectionsService {

    private final CollectionsRepository collectionsRepository;

    @Autowired
    public CollectionsService(CollectionsRepository collectionsRepository) {
        this.collectionsRepository = collectionsRepository;
    }

    public Collection findById(int id) {
        return collectionsRepository.findById(id).get();
    }

    public List<Collection> findByCreator(Person person) {
        return collectionsRepository.findCollectionByCreator(person);
    }

    public byte[] getImageByCollectionId(int id) {
        return findById(id).getImage();
    }

    public List<CollectionItem> getItemsOfCollection(int id) {
        return findById(id).getItemsOfCollection();
    }

    @Transactional
    public void save(Collection collection) {
        collectionsRepository.save(collection);
    }
}
