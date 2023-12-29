package com.example.delivery.service;


import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.model.Customer;
import com.example.delivery.repository.CustomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomeRepository customerRep;
	

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		Optional<Customer> opt = customerRep.findById(customer.getid());
		if(opt.isPresent()) {
			throw new CustomerException("Customer already exists..");
		}else {
			return customerRep.save(customer);
		}
	}


	@Override
	public Customer updateCustomer(Customer customer) throws CustomerException {
		Optional<Customer> opt = customerRep.findById(customer.getid());
		if(opt.isPresent()) {
			return customerRep.save(customer);
		}else {
			throw new CustomerException("No such customer exists..");
		}
	}


	@Override
	public Customer removeCustomerById(Integer customerId) throws CustomerException {
		Optional<Customer> opt = customerRep.findById(customerId);
		if(opt.isPresent()) {
			Customer customer = opt.get();
			customerRep.delete(customer);
			return customer;
		}else {
			throw new CustomerException("No Customer found with ID: "+customerId);
		}
	}


	@Override
	public Customer viewCustomer(Integer customerId) throws CustomerException {
		Optional<Customer> opt = customerRep.findById(customerId);
		if(opt.isPresent()) {
			Customer customer = opt.get();
			return customer;
		}else {
			throw new CustomerException("No Customer found with ID: "+customerId);
		}
	}

	@Override
	public List<Customer> viewall() throws CustomerException {
		List<Customer> customers = customerRep.findAll();
		if(customers.size() > 0) {
			return customers;
		}else {
			throw new CustomerException("No Customers exists..");
		}
	}

}
