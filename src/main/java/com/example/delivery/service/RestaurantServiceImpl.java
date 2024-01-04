package com.example.delivery.service;


import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Item;
import com.example.delivery.model.Restaurant;
import com.example.delivery.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

	@Autowired
	RestaurantRepository restRep;

	
	
	
	@Override
	public Restaurant addRestaurant(Restaurant restaurant) throws RestaurantException {
		Optional<Restaurant> opt = restRep.findById(restaurant.getid());
		if(opt.isPresent()) {
			throw new RestaurantException("Restaurant already exists..");
		}else {
			return restRep.save(restaurant);
		}
	}




	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) throws RestaurantException {
		Optional<Restaurant> opt = restRep.findById(restaurant.getid());
		if(opt.isPresent()) {
			return restRep.save(restaurant);
		}else {
			throw new RestaurantException("No such Restaurant exists..");
		}
	}




	@Override
	public Restaurant removeRestaurant(Integer restaurantId) throws RestaurantException {
		Optional<Restaurant> opt = restRep.findById(restaurantId);
		if(opt.isPresent()) {
			Restaurant restaurant = opt.get();
			restRep.delete(restaurant);
			return restaurant;
		}else {
			throw new RestaurantException("No Restaurant found with ID: "+restaurantId);
		}
	}




	@Override
	public Restaurant viewRestaurant(Integer restaurantId) throws RestaurantException {
		Optional<Restaurant> opt = restRep.findById(restaurantId);
		if(opt.isPresent()) {
			Restaurant restaurant = opt.get();
			return restaurant;
		}else {
			throw new RestaurantException("No Restaurant found with ID: "+restaurantId);
		}
	}

	@Override
	public List<Restaurant> viewall() throws RestaurantException {
		List<Restaurant>  restaurants = restRep.findAll();
		if(restaurants.size() > 0) {
			return restaurants;
		}else {
			throw new RestaurantException("No rest exists..");
		}
	}
	@Override

	public List<Item> getItemsByRestaurantId(Integer restaurantId) {
		if (restaurantId == null) {
			logger.warn("Invalid input: restaurantId is null");
			return Collections.emptyList(); // Or throw an exception for invalid input
		}

		Optional<Restaurant> restaurantOptional = restRep.findById(restaurantId);
		if (restaurantOptional.isPresent()) {
			Restaurant restaurant = restaurantOptional.get();
			List<Item> items = restaurant.getItemList();

			// Log the fetched items to inspect them
			logger.debug("Fetched items for restaurant ID {}: {}", restaurantId, items);
			for (Item item : items) {
				logger.debug("Item ID: {}, Item Name: {}, Cost: {}", item.getid(), item.getItemName(), item.getCost());
				// Log other item details as needed
			}

			return items;
		} else {
			logger.warn("No restaurant found for ID: {}", restaurantId);
			// Handle case when restaurant is not found
			return Collections.emptyList(); // Return an empty list or throw an exception
		}
	}



}
