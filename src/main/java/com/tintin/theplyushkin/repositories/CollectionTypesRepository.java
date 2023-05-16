package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionTypesRepository extends JpaRepository<CollectionType, Integer> {
}
