package com.example.delivery.service;


import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Item;
import com.example.delivery.model.Restaurant;

import java.util.List;


public interface RestaurantService {
	
	public Restaurant addRestaurant(Restaurant restaurant)throws RestaurantException;
	
	public Restaurant updateRestaurant(Restaurant restaurant)throws RestaurantException;
	
	public Restaurant removeRestaurant(Integer restaurantId)throws RestaurantException;
	
	public Restaurant viewRestaurant(Integer restaurantId)throws RestaurantException;
	public List<Restaurant> viewall()throws RestaurantException;



	List<Item> getItemsByRestaurantId(Integer restaurantId);
}
