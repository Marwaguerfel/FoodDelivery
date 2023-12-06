package com.example.delivery.service;


import com.example.delivery.exceptions.BillException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Bill;
import com.example.delivery.model.Customer;
import com.example.delivery.model.Item;
import com.example.delivery.repository.BillRepository;
import com.example.delivery.repository.CustomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService{
	
	@Autowired
	BillRepository billRepo;
	
	@Autowired
	CustomeRepository cusRepo;

	
	
	@Override
	public Bill addBill(Bill bill) throws BillException {
		Optional<Bill> opt = billRepo.findById(bill.getid());
		if(opt.isPresent()) {
			throw new BillException("Bill already exists..");
		}else {
			return billRepo.save(bill);
		}
	}


	@Override
	public Bill updateBill(Bill bill) throws BillException {
		Optional<Bill> opt = billRepo.findById(bill.getid());
		if(opt.isPresent()) {
			return billRepo.save(bill);
		}else {
			throw new BillException("Bill doesn't exists..");
		}
	}


	@Override
	public Bill removeBill(Integer billId) throws BillException {
		Optional<Bill> opt = billRepo.findById(billId);
		if(opt.isPresent()) {
			Bill bill = opt.get();
			billRepo.delete(bill);
			return bill;
		}else {
			throw new BillException("Bill not found with ID: "+billId);
		}
		
	}


	@Override
	public Bill viewBill(Integer billId) throws BillException {
		Optional<Bill> opt = billRepo.findById(billId);
		if(opt.isPresent()) {
			return opt.get();
		}else{
			throw new BillException("Bill not found with ID: "+billId);
		}
	}


	@Override
	public String generateTotalBillById(Integer customerId) throws ItemException, CustomerException {
		Optional<Customer> cOpt = cusRepo.findById(customerId);
		if(cOpt.isPresent()) {
			Customer customer = cOpt.get();
			List<Item> items = customer.getFoodCart().getItemList();
			
			if(items.size() > 0) {
				
				Double total = 0.0;
				for(Item item : items) {
					total += (item.getCost()*item.getQuantity()); 
				}
				
				return "The total bill for "+customer.getFullName()+" is "+total;
				
			}else {
				throw new ItemException("No order items found for "+customer.getFullName());
			}
			
		}else {
			throw new CustomerException("No Customer found with ID: "+customerId);
		}
	}

}
