package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authservice.UserSessionService;
import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.FoodCart;
import com.example.delivery.service.FoodCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class FoodCartServiceController {
	
	@Autowired
	FoodCartService cartService;
	
	@Autowired
	UserSessionService userSessionService;
	
	
	@PostMapping("/register")
	public ResponseEntity<FoodCart> saveCartDetails(@RequestParam String key, @RequestBody FoodCart fc) throws CartException, AuthorizationException
	{
				Integer sessionId = userSessionService.getUserSessionId(key);
				
				if(fc!=null && sessionId != null) {
	            FoodCart f= cartService.saveCart(fc);
	            	return new ResponseEntity<FoodCart>(f,HttpStatus.CREATED);
	            }
	            throw new CartException();
	}
	
	
	@PutMapping("/add/{cartId}/{itemId}")
	public ResponseEntity<FoodCart> addItemToCart(@PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) throws CartException, ItemException {
		FoodCart updatedCart = cartService.addItem(cartId, itemId);
		return new ResponseEntity<FoodCart>(updatedCart, HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/remove/{cartId}")
	public ResponseEntity<FoodCart> removeCart(@PathVariable("cartId") Integer cartId) throws CartException{
		FoodCart removedCart = cartService.clearCart(cartId);
		return new ResponseEntity<FoodCart>(removedCart, HttpStatus.OK);
	}
	
	
	@GetMapping("/view/{cartId}")
	public FoodCart getCartByCartId(@PathVariable ("cartId") Integer cartId,@RequestParam String key) throws AuthorizationException, CartException{
		
		Integer sessionId = userSessionService.getUserSessionId(key);
		if(sessionId != null)
			return cartService.viewCart(cartId);
		else
			 throw new CartException();
			
	}

}
