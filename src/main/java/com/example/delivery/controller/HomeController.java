package com.example.delivery.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("loginSuccessful") String loginSuccessful) {
        model.addAttribute("loginSuccessful", loginSuccessful);
        return "user/index";
    }
}