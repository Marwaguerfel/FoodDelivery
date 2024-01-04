package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Customer;
import com.example.delivery.model.FoodCart;
import com.example.delivery.model.Item;
import com.example.delivery.service.CustomerService;
import com.example.delivery.service.FoodCartService;
import com.example.delivery.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	ItemService itemService;

	@GetMapping("/order")
	public String addPage(Model model)  {
		model.addAttribute("cart", new FoodCart());
		return "User/add-order";
	}

	@PostMapping("/addCart")
	public String saveCartDetails(@ModelAttribute FoodCart fc) throws CartException, AuthorizationException {
		FoodCart f = cartService.saveCart(fc);
		return "redirect:/cart/viewall";

	}

	@PostMapping("/addCust")
	public String addCustomer(@ModelAttribute Customer customer) throws CustomerException {
		Customer newCustomer = custSer.addCustomer(customer);
		return "redirect:/cart/order";
	}

	@GetMapping("/remove/{cartId}")
	public String removeCart(@PathVariable("cartId") Integer cartId) throws CartException {
		FoodCart removedCart = cartService.clearCart(cartId);
		return "redirect:/cart/viewall";
	}


	@GetMapping("/view/{cartId}")
	public FoodCart getCartByCartId(@PathVariable("cartId") Integer cartId) throws AuthorizationException, CartException {
		return cartService.viewCart(cartId);

	}

	@GetMapping("/viewall")
	public String Carts(Model model) throws CartException {
		List<FoodCart> carts = cartService.viewall();
		model.addAttribute("carts", carts);
		return "cart";
	}

	@GetMapping("/item/{cartId}")
	public String getCartItems(@PathVariable Integer cartId) throws CartException {
		FoodCart cart =cartService.viewCart(cartId);
		return "addItemCart";
	}


	@PostMapping("/item/{cartId}")
	public String  addItemToCart(@PathVariable Integer cartId, @ModelAttribute Item item) throws CartException, ItemException {
				FoodCart cart=cartService.addItem(cartId, item.getid()); // Call your existing addItem method
        return "redirect:/cart/viewall";
    }




}




