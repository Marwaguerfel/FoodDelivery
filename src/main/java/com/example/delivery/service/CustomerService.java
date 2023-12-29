package com.example.delivery.service;

import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.model.Customer;

import java.util.List;


public interface CustomerService {
	
	public Customer addCustomer(Customer customer)throws CustomerException;
	
	public Customer updateCustomer(Customer customer)throws CustomerException;
	
	public Customer removeCustomerById(Integer customerId)throws CustomerException;
	
	public Customer viewCustomer(Integer customerId)throws CustomerException;
	public List<Customer> viewall()throws CustomerException;


}
