package com.example.delivery.controller;


import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Category;
import com.example.delivery.model.Restaurant;
import com.example.delivery.service.CategoryService;
import com.example.delivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {
    @Autowired

    CategoryService catService;
    @Autowired
    RestaurantService restaurantService;
    @GetMapping("/")
    public String index(Model model) throws CategoryException , RestaurantException {
        List<Category> categories = catService.viewAllCategory();
        model.addAttribute("categories", categories);
List<Restaurant> restaurants=restaurantService.viewall();
        model.addAttribute("restaurants", restaurants);

        return "user/index";
    }

}