package com.example.delivery.service;


import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.model.Category;
import com.example.delivery.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryRepository categoryRepo;



	
	@Override
	public Category addCategory(Category category) throws CategoryException {
		Optional<Category> opt = categoryRepo.findById(category.getid());
		if(opt.isPresent()) {
			throw new CategoryException("Category already exists..");
		}else {
			return categoryRepo.save(category);
		}
	}



	@Override
	public Category updateCategory(Category category) throws CategoryException {
		Optional<Category> opt = categoryRepo.findById(category.getid());
		if(opt.isPresent()) {
			return categoryRepo.save(category);
		}else {
			throw new CategoryException("No such Category found..");
		}
	}



	@Override
	public Category viewCategory(Integer categoryId) throws CategoryException {
		Optional<Category> opt = categoryRepo.findById(categoryId);
		if(opt.isPresent()) {
			return opt.get();
		}else {
			throw new CategoryException("No Category found with ID: "+categoryId);
		}
	}



	@Override
	public Category removeCategory(int categoryId) throws CategoryException {
		Optional<Category> opt = categoryRepo.findById(categoryId);
		if(opt.isPresent()) {
			Category cat = opt.get();
			categoryRepo.delete(cat);
			return cat;
		}else {
			throw new CategoryException("No Category found with ID: "+categoryId);
		}
	}



	@Override
	public List<Category> viewAllCategory() throws CategoryException {
		List<Category> categories = categoryRepo.findAll();
		if(categories.size() > 0) {
			return categories;
		}else {
			throw new CategoryException("No Categories exists..");
		}
	}
	
	
	

}
