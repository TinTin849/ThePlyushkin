package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.FeatureOfItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesOfItemRepository extends JpaRepository<FeatureOfItem, Integer> {
}
