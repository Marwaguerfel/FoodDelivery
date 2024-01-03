package com.example.delivery.authrepository;

import com.example.delivery.authmodels.LogInModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogInModelRepository extends JpaRepository<LogInModel, Integer>{



}
