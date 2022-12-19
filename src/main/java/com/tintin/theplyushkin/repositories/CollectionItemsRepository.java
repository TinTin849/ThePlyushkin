package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionItemsRepository extends JpaRepository<CollectionItem, Integer> {
    void deleteById(int id);
}
