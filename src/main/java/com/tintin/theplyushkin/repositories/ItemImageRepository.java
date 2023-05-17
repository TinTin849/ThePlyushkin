package com.tintin.theplyushkin.repositories;

import com.tintin.theplyushkin.models.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
    List<ItemImage> findByItemId(Integer id);
}
