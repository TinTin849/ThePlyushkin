package com.tintin.theplyushkin.services;

import com.tintin.theplyushkin.models.ItemFeature;
import com.tintin.theplyushkin.repositories.ItemFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeaturesOfItemService {
    private final ItemFeaturesRepository itemFeaturesRepository;

    @Autowired
    public FeaturesOfItemService(ItemFeaturesRepository itemFeaturesRepository) {
        this.itemFeaturesRepository = itemFeaturesRepository;
    }

    @Transactional
    public void save(ItemFeature itemFeature) {
        itemFeaturesRepository.save(itemFeature);
    }
}
