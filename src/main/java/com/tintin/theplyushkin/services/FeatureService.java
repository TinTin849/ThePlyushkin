package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.Feature;
import com.tintin.theplyushkin.repositories.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeatureService {
    private final FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public Feature findById(int id) {
        return featureRepository.findById(id).get();
    }

    @Transactional
    public void save(Feature feature) {
        featureRepository.save(feature);
    }
}
