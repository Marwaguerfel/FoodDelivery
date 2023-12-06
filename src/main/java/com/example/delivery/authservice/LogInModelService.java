package com.example.delivery.authservice;


import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.LogInModel;

public interface LogInModelService {
	
	public String LogIn(LogInModel login) throws AuthorizationException;
	
	public String LogOut(String key) throws AuthorizationException;

}
