package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.FoodCart;
import com.example.delivery.service.CustomerService;
import com.example.delivery.service.FoodCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class FoodCartServiceController {

	@Autowired
	FoodCartService cartService;

	@Autowired
	CustomerService custSer;

	@GetMapping("/add")
	public String addPage(Model model) throws CustomerException {
		model.addAttribute("cart", new FoodCart());
		model.addAttribute("customers",custSer.viewall() );
		return "addCart";
	}
	@PostMapping("/register")
	public String saveCartDetails(@ModelAttribute FoodCart fc) throws CartException, AuthorizationException
	{
		FoodCart f= cartService.saveCart(fc);
		return "redirect:/cart/viewall";

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
	public FoodCart getCartByCartId(@PathVariable ("cartId") Integer cartId,@RequestParam String key) throws AuthorizationException, CartException {
		return cartService.viewCart(cartId);

	}
	@GetMapping("/viewall")
	public String Carts(Model model) throws CartException {
		List<FoodCart> carts = cartService.viewall();
		model.addAttribute("carts", carts);
		return "cart";
	}
}
