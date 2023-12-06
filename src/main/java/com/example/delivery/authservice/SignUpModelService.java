package com.example.delivery.authservice;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.SignUpModel;


public interface SignUpModelService {
	
	public SignUpModel newSignUp(SignUpModel signUp) throws AuthorizationException;
	
	public SignUpModel updateSignUp(SignUpModel signUp, String key) throws AuthorizationException;

}
