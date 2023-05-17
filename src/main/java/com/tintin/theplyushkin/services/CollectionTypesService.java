package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.CollectionType;
import com.tintin.theplyushkin.repositories.CollectionTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CollectionTypesService {
    private final CollectionTypesRepository collectionTypesRepository;

    @Autowired
    public CollectionTypesService(CollectionTypesRepository collectionTypesRepository) {
        this.collectionTypesRepository = collectionTypesRepository;
    }

    public List<CollectionType> findAll() {
        return collectionTypesRepository.findAll();
    }

    public CollectionType findById(int id) {
        return collectionTypesRepository.findById(id).get();
    }
}
