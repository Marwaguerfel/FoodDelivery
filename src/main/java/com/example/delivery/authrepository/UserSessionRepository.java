package com.example.delivery.authrepository;

import com.example.delivery.authmodels.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
	
	public Optional<UserSession> findByUserId(Integer userId);

	Optional<UserSession> findByUuid(String uuid);

}
