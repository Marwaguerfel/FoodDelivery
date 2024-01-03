package com.example.delivery.controller;


import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.model.Category;
import com.example.delivery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryServiceController {
	
	@Autowired
	CategoryService catService;

	@GetMapping("/add")
	public String addPage(Model model) {
		model.addAttribute("category", new Category());
		return "addCat";
	}
	@PostMapping("/add")
	public String addCategory(@ModelAttribute("category") Category category,Model model) throws CategoryException {
		Category newCategory = catService.addCategory(category);
		return "redirect:viewall";
	}
	@GetMapping("/edit")
	public String getEditPage(
			Model model,
			RedirectAttributes attributes,
			@RequestParam int categoryId
	) {
		String page = null;
		try {
			Category category = catService.viewCategory(categoryId);
			model.addAttribute("category", category);
			page = "updateCat";
		} catch (CategoryException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:viewall";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateCategory(
			@ModelAttribute Category category,
			RedirectAttributes attributes
	) {
		try {
			Category updatedCategory = catService.updateCategory(category);
			attributes.addAttribute("message", "Category with id: '" + updatedCategory.getid() + "' is updated successfully!");
		} catch (CategoryException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:viewall";
	}

	
	@GetMapping("/view/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable("categoryId") int categoryId) throws CategoryException{
		Category category = catService.viewCategory(categoryId);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}


	@GetMapping("/remove/{id}")
	public String removeCategory(
			@PathVariable int id,
			RedirectAttributes attributes
	) {
		try {
			catService.removeCategory(id);
			attributes.addAttribute("message", "Category with Id: '" + id + "' is removed successfully!");
		} catch (CategoryException  e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:/viewall";
	}



	@GetMapping("/viewall")
	public String getAllCategories(Model model) throws CategoryException {
		List<Category> categories = catService.viewAllCategory();
		model.addAttribute("categories", categories);
		return "categoryList";
	}
	

}
