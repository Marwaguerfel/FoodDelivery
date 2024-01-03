package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authservice.UserSessionService;
import com.example.delivery.exceptions.CartException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.OrderException;
import com.example.delivery.model.FoodCart;
import com.example.delivery.model.Item;
import com.example.delivery.model.OrderDetails;
import com.example.delivery.service.FoodCartService;
import com.example.delivery.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderDetailServiceController {
	
	@Autowired
	OrderDetailService orderService;
	
	@Autowired
	UserSessionService userSessionService;
	@Autowired
	FoodCartService foodCartService;

	@GetMapping("/createOrder/{cartId}")
	public String showOrderForm(@PathVariable("cartId") int cartId, Model model) throws CartException,OrderException{
		FoodCart cart = foodCartService.viewCart(cartId);
		OrderDetails order = new OrderDetails();
		order.setCart(cart);
		model.addAttribute("order", order);
		return "placeOrder";
	}

	@PostMapping("/createOrder/{cartId}")
	public String submitOrderForm(
			@PathVariable("cartId") Integer cartId,
			@ModelAttribute("orderForm") OrderDetails orderForm) throws OrderException{
		try {
			FoodCart cart = foodCartService.viewCart(cartId);
			OrderDetails order = new OrderDetails();
			order.setOrderDate(orderForm.getOrderDate());
			order.setOrderStatus(orderForm.getOrderStatus());
			order.setCart(cart);
			orderService.addOrder(order);
			return "redirect:/order/viewall";
		} catch (CartException e) {
			return "errorPage";
		}
	}



	@GetMapping("/edit")
	public String editOrder(
			Model model,
			@RequestParam int orderId
	)throws OrderException{
		OrderDetails order = orderService.viewOrder(orderId);
		model.addAttribute("order",order);
		return "editOrder";

	}


	@PostMapping("/update")
     public String updateOrder(@ModelAttribute("order") OrderDetails order) throws OrderException{
		OrderDetails orderUp =orderService.updateOrder(order);
		return "redirect:/order/viewall";

     }
	 
	 
	 @GetMapping("/remove/{orderId}")
     public String deleteOrder(@PathVariable("orderId") Integer orderId) throws OrderException, AuthorizationException {
		OrderDetails Remo=orderService.removeOrder(orderId);
		return "redirect:/order/viewall";

     }
	 
	 @GetMapping("/view/{orderId}")
     public ResponseEntity<OrderDetails> viewOrder(@PathVariable("orderId") Integer orderId,@RequestParam String key) throws OrderException, AuthorizationException{
     	
     	Integer sessionId = userSessionService.getUserSessionId(key);
     	
     	if(sessionId != null)
     		return  new ResponseEntity<OrderDetails>(orderService.viewOrder(orderId),HttpStatus.FOUND);
     	else
     		throw new OrderException();
     }
	 
	 
	 @GetMapping("/viewbycustomer/{customerId}")
     public String viewAllOrders(@PathVariable("customerId") Integer customerId ,Model model) throws OrderException, CustomerException, AuthorizationException{
		List<Item> orders= orderService.viewAllOrdersByCustomer(customerId);
		model.addAttribute("orders",orders);
		return "OrderByCust";
     }

	@GetMapping("/viewall")
	public String viewall(Model model) throws OrderException {
		List<OrderDetails> orders = orderService.viewall();
		model.addAttribute("orders", orders);
		return "ordersList";
	}

}
