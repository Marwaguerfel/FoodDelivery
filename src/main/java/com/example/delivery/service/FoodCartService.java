package com.example.delivery.service;


import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.FoodCart;

import java.util.List;

public interface FoodCartService {
	
	public FoodCart saveCart(FoodCart cart)throws CartException;
	
	public FoodCart addItem(Integer cartId, Integer itemId)throws CartException, ItemException;
	
	public FoodCart clearCart(Integer cartId)throws CartException;
	
	public FoodCart viewCart(Integer cartId)throws CartException;

	public List<FoodCart> viewall()throws CartException;


}
