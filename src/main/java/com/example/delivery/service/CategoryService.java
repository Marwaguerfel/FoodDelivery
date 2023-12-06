package com.example.delivery.service;


import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.model.Category;

import java.util.List;

public interface CategoryService {
	
	
	public Category addCategory(Category category)throws CategoryException;
	
	public Category updateCategory(Category category)throws CategoryException;
	
	public Category viewCategory(Integer categoryId)throws CategoryException;
	
	public Category removeCategory(Integer categoryId)throws CategoryException;
	
	public List<Category> viewAllCategory()throws CategoryException;

}
