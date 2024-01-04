package com.example.delivery.authcontroller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.LogInModel;
import com.example.delivery.authmodels.SignUpModel;
import com.example.delivery.authservice.SignUpModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
public class SignUpController {
	
	@Autowired
	private SignUpModelService signUpService;
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registered", new SignUpModel());
		return "auth/register"; // Assuming "register" is the name of your register page
	}


	@PostMapping("/register")
	public String createNewSignUpHandler(@ModelAttribute SignUpModel newSignUp) throws AuthorizationException {
		SignUpModel newSignedUp = signUpService.newSignUp(newSignUp);
		return "redirect:/login";
	}

	@PutMapping("/updateSignUp")
	public ResponseEntity<SignUpModel> updateSignUpDetailsHandler(@RequestBody SignUpModel signUp, @RequestParam String key) throws AuthorizationException
	{
		SignUpModel newUpdatedSignUp = signUpService.updateSignUp(signUp,key);
		
		return new ResponseEntity<SignUpModel>(newUpdatedSignUp,HttpStatus.ACCEPTED);
		
	
	}


}
