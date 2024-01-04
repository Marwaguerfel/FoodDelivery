package com.example.delivery.service;


import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Item;
import com.example.delivery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	ItemRepository itemRepo ;

	
	
	@Override
	public Item addItem(Item item) throws ItemException {
		Optional<Item> opt = itemRepo.findById(item.getid());
		if(opt.isPresent()) {
			throw new ItemException("Item already exists..");
		}else {
			return itemRepo.save(item);
		}
	}



	@Override
	public Item updateItem(Item item) throws ItemException {
		Optional<Item> opt = itemRepo.findById(item.getid());
		if(opt.isPresent()) {
			return itemRepo.save(item);
		}else {
			throw new ItemException("No such Item found..");
		}
	}



	@Override
	public Item viewItem(Integer itemId) throws ItemException {
		Optional<Item> opt = itemRepo.findById(itemId);
		if(opt.isPresent()) {
			return opt.get();
		}else {
			throw new ItemException("No Item found with ID: "+itemId);
		}
	}



	@Override
	public Item removeItem(Integer itemId) throws ItemException {
		Optional<Item> opt = itemRepo.findById(itemId);
		if(opt.isPresent()) {
			Item item = opt.get();
			itemRepo.delete(item);
			return item;
		}else {
			throw new ItemException("No Item found with ID: "+itemId);
		}
	}



	@Override
	public List<Item> viewAllItems() throws ItemException {
		List<Item> items = itemRepo.findAll();
		if(items.size() > 0) {
			return items;
		}else {
			throw new ItemException("No Item exists..");
		}
	}
	@Override

	public List<Item> getItemsByCategoryId(int categoryId) {
		return itemRepo.findByCategoryId(categoryId);
	}
	@Override

	public List<Item> getItemsByRestaurantAndCategoryId(int restaurantId, int categoryId) {
		// Implement logic to fetch items for a specific restaurant and category
		// This could involve a custom query in your repository.
		return itemRepo.findByRestaurantIdAndCategoryId(restaurantId, categoryId);
	}
}
