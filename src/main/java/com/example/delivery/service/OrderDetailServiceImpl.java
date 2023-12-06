package com.example.delivery.service;


import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.OrderException;
import com.example.delivery.model.Customer;
import com.example.delivery.model.Item;
import com.example.delivery.model.OrderDetails;
import com.example.delivery.repository.CustomeRepository;
import com.example.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	CustomeRepository cusRepo;

	
	
	@Override
	public OrderDetails addOrder(OrderDetails order) throws OrderException {
		Optional<OrderDetails> opt = orderRepo.findById(order.getid());
		if(opt.isPresent()) {
			throw new OrderException("Order already exists..");
		}else {
			return orderRepo.save(order);
		}
	}



	@Override
	public OrderDetails updateOrder(OrderDetails order) throws OrderException {
		Optional<OrderDetails> opt = orderRepo.findById(order.getid());
		if(opt.isPresent()) {
			return orderRepo.save(order);
		}else {
			throw new OrderException("Order such Order exists..");
		}
	}



	@Override
	public OrderDetails removeOrder(Integer orderId) throws OrderException {
		Optional<OrderDetails> opt = orderRepo.findById(orderId);
		if(opt.isPresent()) {
			OrderDetails order = opt.get();
			orderRepo.delete(order);
			return order;
		}else {
			throw new OrderException("No Order found with ID: "+orderId);
		}
	}



	@Override
	public OrderDetails viewOrder(Integer orderId) throws OrderException {
		Optional<OrderDetails> opt = orderRepo.findById(orderId);
		if(opt.isPresent()) {
			OrderDetails order = opt.get();
			return order;
		}else {
			throw new OrderException("No Order found with ID: "+orderId);
		}
	}



	@Override
	public List<Item> viewAllOrdersByCustomer(Integer customerId) throws OrderException, CustomerException {
		Optional<Customer> cOpt =cusRepo.findById(customerId);
		if(cOpt.isPresent()) {
			Customer customer = cOpt.get();
			 List<Item> itemList = customer.getFoodCart().getItemList();
			 if(itemList.size() > 0) {
				 return itemList;
			 }else {
				 throw new OrderException("No Orders found..");
			 }
		}else {
			throw new CustomerException("No Customer found with ID: "+customerId);
		}
	}

}
