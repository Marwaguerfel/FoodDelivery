package com.example.delivery.controller;

import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.model.Category;
import com.example.delivery.model.Item;
import com.example.delivery.service.CategoryService;
import com.example.delivery.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemServiceController {
	
	@Autowired
	CategoryService catSer;
	@Autowired
	ItemService itemService;

	@GetMapping("/add")
	public String addPage(Model model) throws CategoryException {
		model.addAttribute("item", new Item());
		model.addAttribute("categories", catSer.viewAllCategory()); // Assuming catService is your category service
		return "addItem";
	}


	@PostMapping("/add")
	public String addItem(@ModelAttribute("item") Item item, Model model) throws ItemException,CategoryException {
		try {
			int categoryId = item.getCategory().getid();
			Category category = catSer.viewCategory(categoryId);
			item.setCategory(category);
			Item newItem = itemService.addItem(item);

			return "redirect:viewall";
		} catch (CategoryException e) {
			// Handle the CategoryException, for example, by redirecting to the add page with an error message
			model.addAttribute("error", "Error adding item: " + e.getMessage());
			model.addAttribute("item", item); // To repopulate the form with the submitted data
			model.addAttribute("categories", catSer.viewAllCategory());
			return "addItem";
		}
	}
	@GetMapping("/edit")
	public String edit(Model model,
					   RedirectAttributes attributes,
					   @RequestParam int id
	) throws CategoryException,ItemException {
		Item item=itemService.viewItem(id);
		model.addAttribute("item", item);
		model.addAttribute("categories", catSer.viewAllCategory()); // Assuming catService is your category service
		return "editItem" ;
	}

	@PostMapping("/update")
	public String updateItem(@ModelAttribute("item") Item item) throws ItemException {
		Item updatedItem = itemService.updateItem(item);
		return "redirect:viewall";
	}

	
	@GetMapping("/view/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable("itemId") Integer itemId) throws ItemException{
		Item item = itemService.viewItem(itemId);
		return new ResponseEntity<Item>(item, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/remove/{id}")
	public String removeItem(@PathVariable("id") int id) throws ItemException{
		Item removedItem = itemService.removeItem(id);
		return "redirect:/item/viewall";
	}

	
	@GetMapping("/viewall")
	public String getAllItems( Model model) throws ItemException{
		List<Item> items = itemService.viewAllItems();
		model.addAttribute("items",items);
		return "itemList";
		
	}
}
