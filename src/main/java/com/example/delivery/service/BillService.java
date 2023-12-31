package com.example.delivery.service;


import com.example.delivery.exceptions.BillException;
import com.example.delivery.exceptions.CustomerException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Bill;

public interface BillService {
	
	public Bill addBill(Bill bill) throws BillException;

	public Bill updateBill(Bill bill)throws BillException;
	
	public Bill removeBill(Integer billId)throws BillException;
	
	public Bill viewBill(Integer billId)throws BillException;
	
	public Double generateTotalBillById(Integer customerId)throws ItemException, CustomerException;
	

}
