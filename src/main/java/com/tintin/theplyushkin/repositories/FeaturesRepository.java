package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Integer> {
}
