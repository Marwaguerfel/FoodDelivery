package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authservice.UserSessionService;
import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Restaurant;
import com.example.delivery.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/restaurant")
public class RestaurantServiceController {
	
	@Autowired
	RestaurantService restService;
	
	@Autowired
	UserSessionService userSessionService;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<Restaurant> saveResturant(@Validated @RequestBody Restaurant restaurant) throws RestaurantException {
		
		Restaurant newRestaurant = restService.addRestaurant(restaurant);
		
		return new ResponseEntity<Restaurant>(newRestaurant ,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Restaurant> updateResturant(@Validated @RequestBody Restaurant restaurant) throws RestaurantException{
		
		Restaurant updatedResturant=restService.updateRestaurant(restaurant);
		
		return new ResponseEntity<Restaurant>(updatedResturant,HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/remove/{restaurantId}")
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) throws RestaurantException{
		Restaurant removedRestaurant = restService.removeRestaurant(restaurantId);
		return new ResponseEntity<Restaurant>(removedRestaurant, HttpStatus.OK);
	}
	
	
	@GetMapping("/view/{restaurantId}")
	public ResponseEntity<Restaurant> getByResturantId(@PathVariable ("restaurantId") Integer restaurantId ,@RequestParam String key) throws RestaurantException, AuthorizationException {
		
		Integer sessionId = userSessionService.getUserSessionId(key);
    	
    	if(sessionId != null)
    		{	Restaurant restaurant =restService.viewRestaurant(restaurantId);	
    			return new ResponseEntity<Restaurant>(restaurant ,HttpStatus.ACCEPTED);
    		}
    	else
    		throw new RestaurantException();
	}

}
