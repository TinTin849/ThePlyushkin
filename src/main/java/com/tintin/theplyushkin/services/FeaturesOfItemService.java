package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.FeatureOfItem;
import com.tintin.theplyushkin.repositories.FeaturesOfItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeaturesOfItemService {
    private final FeaturesOfItemRepository featuresOfItemRepository;

    @Autowired
    public FeaturesOfItemService(FeaturesOfItemRepository featuresOfItemRepository) {
        this.featuresOfItemRepository = featuresOfItemRepository;
    }

    @Transactional
    public void save(FeatureOfItem featureOfItem) {
        featuresOfItemRepository.save(featureOfItem);
    }
}
