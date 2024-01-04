package com.example.delivery.service;



import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Item;

import java.util.List;

public interface ItemService {
	
	public Item addItem(Item item)throws ItemException;
	
	public Item updateItem(Item item)throws ItemException;
	
	public Item viewItem(Integer itemId)throws ItemException;
	
	public Item removeItem(Integer itemId)throws ItemException;
	
	public List<Item> viewAllItems()throws ItemException;

	List<Item> getItemsByCategoryId(int categoryId);

	List<Item> getItemsByRestaurantAndCategoryId(int restaurantId, int categoryId);
}
