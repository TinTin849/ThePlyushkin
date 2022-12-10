package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.TypeOfCollection;
import com.tintin.theplyushkin.repositories.TypesOfCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TypesOfCollectionService {
    private final TypesOfCollectionRepository typesOfCollectionRepository;

    @Autowired
    public TypesOfCollectionService(TypesOfCollectionRepository typesOfCollectionRepository) {
        this.typesOfCollectionRepository = typesOfCollectionRepository;
    }

    public List<TypeOfCollection> findAll() {
        return typesOfCollectionRepository.findAll();
    }

    public TypeOfCollection findById(int id) {
        return typesOfCollectionRepository.findById(id).get();
    }
}
