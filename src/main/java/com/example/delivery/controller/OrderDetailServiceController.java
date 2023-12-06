package com.example.delivery.controller;

import com.example.delivery.authexceptions.AuthorizationException;
import com.example.delivery.authservice.UserSessionService;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.OrderException;
import com.example.delivery.model.Item;
import com.example.delivery.model.OrderDetails;
import com.example.delivery.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderDetailServiceController {
	
	@Autowired
	OrderDetailService orderService;
	
	@Autowired
	UserSessionService userSessionService;
	
	
	
	 @PostMapping("/save")
     public ResponseEntity<OrderDetails> saveOrder(@RequestBody OrderDetails order, @RequestParam String key) throws OrderException, AuthorizationException{
     	
     	Integer sessionId = userSessionService.getUserSessionId(key);
     	
     	if(sessionId != null)
     		return new ResponseEntity<OrderDetails>(orderService.addOrder(order), HttpStatus.CREATED);
     	else
     		throw new OrderException();
     }
	 
	 
	 @PutMapping("/update")
     public ResponseEntity<OrderDetails> updateOrder(@RequestBody OrderDetails order, @RequestParam String key) throws OrderException, AuthorizationException{
         
     	Integer sessionId = userSessionService.getUserSessionId(key);
     	
     	if(sessionId != null)
     		return new ResponseEntity<OrderDetails>(orderService.updateOrder(order), HttpStatus.ACCEPTED);
     	else
     		throw new OrderException();
     		
     }
	 
	 
	 @DeleteMapping("/remove/{orderId}")
     public ResponseEntity<OrderDetails> deleteOrder(@PathVariable("orderId") Integer orderId , @RequestParam String key) throws OrderException, AuthorizationException {
     	
     	Integer sessionId = userSessionService.getUserSessionId(key);
     	
     	if(sessionId != null) 
     		return new ResponseEntity<OrderDetails>(orderService.removeOrder(orderId), HttpStatus.ACCEPTED);
     	
     	else
     		throw new OrderException();
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
     public ResponseEntity<List<Item>> viewAllOrders(@PathVariable("customerId") Integer customerId, @RequestParam String key) throws OrderException, CustomerException, AuthorizationException{
     	
     	Integer sessionId = userSessionService.getUserSessionId(key);
     	
     	if(sessionId != null)
     		return  new ResponseEntity<List<Item>>(orderService.viewAllOrdersByCustomer(customerId), HttpStatus.FOUND);
     	else
     		throw new OrderException();
     }

}
