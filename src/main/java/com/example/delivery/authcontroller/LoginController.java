package com.example.delivery.authcontroller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authmodels.LogInModel;
import com.example.delivery.authmodels.SignUpModel;
import com.example.delivery.authservice.LogInModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@Autowired
	private LogInModelServiceImpl loginService;

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginData", new LogInModel());
		return "auth/login";
	}
	@PostMapping("/login")
	public String loginHandler(@ModelAttribute("loginData") LogInModel loginData, RedirectAttributes redirectAttributes) throws AuthorizationException {
		String loginSuccessful = loginService.LogIn(loginData);
		if (loginSuccessful != null) {
			redirectAttributes.addFlashAttribute("loginSuccessful", loginSuccessful);
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password");
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logOutFromAccount(@RequestParam("loginSuccessful") String loginSuccessful, RedirectAttributes redirectAttributes) {
		try {
			loginService.LogOut(loginSuccessful);
			redirectAttributes.addFlashAttribute("successMessage", "Logout successful!");
		} catch (AuthorizationException e) {
			redirectAttributes.addFlashAttribute("error", "Error during logout: " + e.getMessage());
		}
		return "redirect:/login";
	}


}