package com.example.delivery.controller;

import com.example.delivery.authservice.UserSessionService;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Restaurant;
import com.example.delivery.service.ItemService;
import com.example.delivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class RestaurantServiceController {

	@Autowired
	private RestaurantService restService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserSessionService userSessionService;

	@GetMapping("/add")
	public String showAddRestaurantForm(Model model) throws ItemException {

		model.addAttribute("restaurant", new Restaurant());
		model.addAttribute("allItems", itemService.viewAllItems());
		return "addRest";
	}

	@PostMapping("/add")
	public String addRestaurant(@Validated @ModelAttribute Restaurant restaurant) throws RestaurantException {
		restService.addRestaurant(restaurant);
		return "redirect:admin/restaurants";
	}

	@GetMapping("/edit")
	public String editRestaurantPage(
			Model model,
			@RequestParam int restaurantId
	)throws RestaurantException,ItemException{
			Restaurant restaurant = restService.viewRestaurant(restaurantId);
			model.addAttribute("allItems", itemService.viewAllItems());
			model.addAttribute("restaurant", restaurant);
			return "editRest";

	}

	@PostMapping("/update")
	public String updateResturant(@ModelAttribute("restaurant") Restaurant restaurant) throws RestaurantException {
			Restaurant updatedResturant = restService.updateRestaurant(restaurant);
			return "redirect:/restaurant/viewall";
	}


	@GetMapping("/remove/{restaurantId}")
	public String deleteRes(@PathVariable("restaurantId") Integer restaurantId) throws RestaurantException {
		restService.removeRestaurant(restaurantId);
		return "redirect:/restaurant/viewall";
	}

	@GetMapping("/view/{restaurantId}")
	public String viewRestaurantDetails(
			@PathVariable("restaurantId") Integer restaurantId,
			Model model
	) throws RestaurantException {

			Restaurant restaurant = restService.viewRestaurant(restaurantId);
			model.addAttribute("restaurant", restaurant);
			return "restDet";

	}

	@GetMapping("/viewall")
	public String viewAllRestaurants(Model model, @ModelAttribute("loginSuccessful") String loginSuccessful) throws RestaurantException {
		List<Restaurant> restaurants = restService.viewall();
		model.addAttribute("restaurants", restaurants);
		model.addAttribute("loginSuccessful", loginSuccessful);
		return "restList";
	}
}
