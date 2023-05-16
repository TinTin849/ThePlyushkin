package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.Collection;
import com.tintin.theplyushkin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionsRepository extends JpaRepository<Collection, Integer> {
    List<Collection> findCollectionByUser(User user);
}