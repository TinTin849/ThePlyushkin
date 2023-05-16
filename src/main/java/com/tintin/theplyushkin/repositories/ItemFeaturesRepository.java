package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.ItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFeaturesRepository extends JpaRepository<ItemFeature, Integer> {
}
