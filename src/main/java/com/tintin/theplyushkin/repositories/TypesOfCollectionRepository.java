package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.TypeOfCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesOfCollectionRepository extends JpaRepository<TypeOfCollection, Integer> {
}
