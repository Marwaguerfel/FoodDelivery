package com.example.delivery.repository;

import com.example.delivery.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer>{

}
