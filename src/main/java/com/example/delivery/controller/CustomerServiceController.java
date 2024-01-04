package com.example.delivery.controller;


import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.model.Customer;
import com.example.delivery.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerServiceController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/add")
	public String addPage(Model model) {
		model.addAttribute("customer", new Customer());
		return "addCust";
	}

	@PostMapping("/add")
	public String addCustomer(@ModelAttribute Customer customer) throws CustomerException {
		Customer newCustomer = customerService.addCustomer(customer);
		return "redirect:/customer/viewall";
	}

	@GetMapping("/viewall")
	public String listCustomers(Model model) throws CustomerException{
		List<Customer> customers = customerService.viewall();
		model.addAttribute("customers", customers);
		return "admin/CustomerList";
	}

	@GetMapping("/edit")
	public String editCustomerPage(@RequestParam("customerId") Integer customerId, Model model) throws CustomerException {
		Customer customer = customerService.viewCustomer(customerId);
		model.addAttribute("customer", customer);
		return "editCust";
	}

	@PostMapping("/update")
	public String updateCustomer(@ModelAttribute("customer") Customer customer) throws CustomerException {
		customerService.updateCustomer(customer);
		return "redirect:/customer/viewall";
	}

	@GetMapping("/remove/{customerId}")
	public String deleteCustomer(@PathVariable("customerId") Integer customerId) throws CustomerException {
		customerService.removeCustomerById(customerId);
		return "redirect:/customer/viewall";
	}

	@GetMapping("/view/{customerId}")
	public String viewCustomer(@PathVariable("customerId") Integer customerId, Model model) throws CustomerException {
		Customer customer = customerService.viewCustomer(customerId);
		model.addAttribute("customer", customer);
		return "customerDetails";
	}
}
