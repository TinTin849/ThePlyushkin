package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    void deleteById(int id);
}
