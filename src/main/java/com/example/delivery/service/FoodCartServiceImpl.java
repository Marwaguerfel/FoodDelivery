package com.example.delivery.service;

import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.FoodCart;
import com.example.delivery.model.Item;
import com.example.delivery.repository.FoodCartRepository;
import com.example.delivery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodCartServiceImpl implements FoodCartService{
	
	@Autowired
	FoodCartRepository cartRepo;

	@Autowired
	ItemRepository itemRepo;
	
	
	@Override
	public FoodCart saveCart(FoodCart cart) throws CartException {
		Optional<FoodCart> opt = cartRepo.findById(cart.getid());
		if(opt.isPresent()) {
			throw new CartException("Cart already exists..");
		}else {
			 return cartRepo.save(cart);
		}
	}


	@Override
	public FoodCart clearCart(Integer cartId) throws CartException {
		Optional<FoodCart> opt = cartRepo.findById(cartId);
		if(opt.isPresent()) {
			FoodCart cart = opt.get();
			cartRepo.delete(cart);
			return cart;
		}else {
			throw new CartException("No Cart found with ID: "+cartId);
		}
	}


	@Override
	public FoodCart viewCart(Integer cartId) throws CartException {
		Optional<FoodCart> opt = cartRepo.findById(cartId);
		if(opt.isPresent()) {
			FoodCart cart = opt.get();
			return cart;
		}else {
			throw new CartException("No Cart found with ID: "+cartId);
		}
	}


	@Override
	public FoodCart addItem(Integer cartId, Integer itemId) throws CartException, ItemException {
		Optional<FoodCart> cOpt = cartRepo.findById(cartId);
		if(cOpt.isPresent()) {
			
			Optional<Item> iOpt = itemRepo.findById(itemId);
			if(iOpt.isPresent()) {
				
				FoodCart cart = cOpt.get();
				Item item = iOpt.get();
				List<Item> list = new ArrayList<>();
				list.addAll(cart.getItemList());
				list.add(item);
				cart.setItemList(list);
				
				return cart;
				
			}else {
				throw new ItemException("No Item found with ID: "+itemId);
			}
			
		}else {
			throw new CartException("No Cart found with ID: "+cartId);
		}
	}


	@Override
	public List<FoodCart> viewall() throws CartException {
		List<FoodCart> carts = cartRepo.findAll();
		if(carts.size() > 0) {
			return carts;
		}else {
			throw new CartException("No Carts exists..");
		}
	}



}
