package com.example.delivery.repository;

import com.example.delivery.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomeRepository extends JpaRepository<Customer, Integer>{

}
