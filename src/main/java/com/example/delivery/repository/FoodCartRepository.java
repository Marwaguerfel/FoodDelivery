package com.example.delivery.repository;

import com.example.delivery.model.FoodCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodCartRepository extends JpaRepository<FoodCart, Integer>{

}
