package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.Item;
import com.tintin.theplyushkin.models.User;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
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

    public List<Collection> findByUser(User user) {
        return collectionsRepository.findCollectionByUser(user);
    }

    public List<Item> getItemsOfCollection(int id) {
        return findById(id).getItems();
    }

    public List<Collection> findPublic() {
        return collectionsRepository.findByVisibility(VisibilityLevel.PUBLIC);
    }

    @Transactional
    public Collection save(Collection collection) {
        return collectionsRepository.save(collection);
    }

    @Transactional
    public void deleteById(int id) {
        collectionsRepository.deleteById(id);
    }
}
