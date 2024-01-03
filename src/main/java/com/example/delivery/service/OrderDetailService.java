package com.example.delivery.service;



import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.OrderException;
import com.example.delivery.model.Item;
import com.example.delivery.model.OrderDetails;

import java.util.List;

public interface OrderDetailService {
	
	public OrderDetails addOrder(OrderDetails order)throws OrderException;
	
	public OrderDetails updateOrder(OrderDetails order)throws OrderException;
	
	public OrderDetails removeOrder(Integer orderId)throws OrderException;
	
	public OrderDetails viewOrder(Integer orderId)throws OrderException;
	
	public List<Item> viewAllOrdersByCustomer(Integer customerId)throws OrderException, CustomerException;
	public List<OrderDetails> viewall()throws OrderException;


}
