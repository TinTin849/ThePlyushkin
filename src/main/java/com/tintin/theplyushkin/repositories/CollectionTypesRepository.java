package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.CollectionType;
import com.tintin.theplyushkin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionTypesRepository extends JpaRepository<CollectionType, Integer> {
    List<CollectionType> findByUser(User user);
}
