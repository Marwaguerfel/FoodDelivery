package com.example.delivery.repository;

import com.example.delivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
    List<Item> findByCategoryId(int categoryId);
    List<Item> findByRestaurantIdAndCategoryId(int categoryId, int restaurantId);

}
