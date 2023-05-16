package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.Feature;
import com.tintin.theplyushkin.repositories.FeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeatureService {
    private final FeaturesRepository featuresRepository;

    @Autowired
    public FeatureService(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    public Feature findById(int id) {
        return featuresRepository.findById(id).get();
    }

    @Transactional
    public void save(Feature feature) {
        featuresRepository.save(feature);
    }
}
