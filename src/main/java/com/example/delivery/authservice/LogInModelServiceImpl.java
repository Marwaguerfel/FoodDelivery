package com.example.delivery.authservice;


import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.LogInModel;
import com.example.delivery.authmodels.SignUpModel;
import com.example.delivery.authmodels.UserSession;
import com.example.delivery.authrepository.LogInModelRepository;
import com.example.delivery.authrepository.SignUpModelRepository;
import com.example.delivery.authrepository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LogInModelServiceImpl implements LogInModelService {
	

	@Autowired
	private SignUpModelRepository signUpRep;
	
	@Autowired
	private UserSessionRepository userSessionRep;
	
	@Autowired
	private UserSessionService userLogInSession;
	
	@Autowired
	private LogInModelRepository loginDataRep;
	
	@Override
	public String LogIn(LogInModel loginData) throws AuthorizationException {

		Optional<SignUpModel> opt = signUpRep.findByUserName(loginData.getUserName());

		if (!opt.isPresent()) {
			throw new AuthorizationException("Invalid Login UserName");
		}

		SignUpModel newSignUp = opt.get();
		Integer newSignUpId = newSignUp.getId();

		Optional<UserSession> currentUserOptional = userSessionRep.findByUserId(newSignUpId);

		if (currentUserOptional.isPresent()) {
			throw new AuthorizationException("User Already LoggedIn with this UserId");
		}

		if (newSignUp.getUserName().equals(loginData.getUserName()) && newSignUp.getPassword().equals(loginData.getPassword()))  {
			String key = RandomString.getRandomString();
			UserSession currentUserSession = new UserSession(newSignUp.getId(), key, LocalDateTime.now());
			userSessionRep.save(currentUserSession);
			return currentUserSession.toString();
		} else {
			throw new AuthorizationException("Invalid UserName or Password");
		}
	}

	@Override
	public String LogOut(String key) throws AuthorizationException {
		
		Optional<UserSession> currentUserOptional = userSessionRep.findByUuid(key);
		if(!currentUserOptional.isPresent())
		{
			throw new AuthorizationException("Invalid credentials...");
		}
		UserSession currentUserSession = userLogInSession.getUserSession(key);
		
		userSessionRep.delete(currentUserSession);
		
		Optional<LogInModel> loginData = loginDataRep.findById(currentUserOptional.get().getId());
		System.out.println(loginData);
		loginDataRep.delete(loginData.get());
		
		
		return "Logged Out...";
	
	}

}
