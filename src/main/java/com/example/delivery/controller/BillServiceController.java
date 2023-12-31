package com.example.delivery.controller;


import com.example.delivery.exceptions.BillException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.exceptions.OrderException;
import com.example.delivery.model.Bill;
import com.example.delivery.model.OrderDetails;
import com.example.delivery.service.BillService;
import com.example.delivery.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/bill")
public class BillServiceController {
	
	@Autowired
	BillService billService;

	@Autowired
	OrderDetailService orderService;

	@GetMapping("/add/{orderId}/{custId}")
	public String showBillForm(@PathVariable("orderId") int orderId,@PathVariable("custId") int custId, Model model) throws OrderException, BillException,CustomerException,ItemException {
		OrderDetails order = orderService.viewOrder(orderId); // Get order details

		Bill bill = new Bill();
		bill.setBillDate(LocalDateTime.now().toString());
		bill.setTotalItem(order.getCart().getItemList().size());
		bill.setTotalCost(getTotalByCustomerId(custId));
		bill.setOrder(order);
		model.addAttribute("order", order);
		model.addAttribute("bill", bill);
		return "addBill";
	}


	@PostMapping("/add")
	public String  generateBill(@ModelAttribute Bill bill) throws BillException {
		Bill myBill = billService.addBill(bill);
		return "redirect:/view/{billId}";

	}
	
	@PutMapping("/update")
	public ResponseEntity<Bill> updateBill(@RequestBody Bill bill) throws BillException {
		Bill myBill = billService.updateBill(bill);
		return new ResponseEntity<Bill>(myBill, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/remove/{billId}")
	public ResponseEntity<Bill> removeBill(@PathVariable("billId") Integer billId ) throws BillException{
		Bill removedBill = billService.removeBill(billId);
		return new ResponseEntity<Bill>(removedBill, HttpStatus.OK);
	}
	
	@GetMapping("/view/{billId}")
	public String viewBill(@PathVariable("billId") Integer billId) throws BillException{
		Bill bill = billService.viewBill(billId);
		return "viewbill";
	}
	
	
	@GetMapping("/viewtotal/{customerId}")
	public Double getTotalByCustomerId(@PathVariable("customerId") Integer customerId) throws ItemException, CustomerException {
		return billService.generateTotalBillById(customerId);
	}

}
