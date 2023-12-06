package com.example.delivery.authservice;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.SignUpModel;
import com.example.delivery.authmodels.UserSession;

public interface UserSessionService {
	
	public UserSession getUserSession(String key) throws AuthorizationException;
	
	public Integer getUserSessionId(String key) throws AuthorizationException;
	
	public SignUpModel getSignUpDetails(String key) throws AuthorizationException;
	

}
