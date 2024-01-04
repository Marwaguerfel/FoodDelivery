package com.example.delivery.controller;

import com.example.delivery.exceptions.CategoryException;
import com.example.delivery.exceptions.ItemException;
import com.example.delivery.exceptions.RestaurantException;
import com.example.delivery.model.Category;
import com.example.delivery.model.Item;
import com.example.delivery.model.Restaurant;
import com.example.delivery.service.CategoryService;
import com.example.delivery.service.ItemService;
import com.example.delivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Import PathVariable
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/oneRestaurant")
public class restaurantIndexController {
    @Autowired
    private CategoryService catService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String index( @PathVariable int id, Model model) throws CategoryException, RestaurantException, ItemException {
        List<Category> categories = catService.viewAllCategory();
        model.addAttribute("categories", categories);
        List<Item> items = restaurantService.getItemsByRestaurantId(id);
        model.addAttribute("items", items);
        List<Restaurant> restaurants = restaurantService.viewall();
        model.addAttribute("restaurants", restaurants);

        return "restaurantPage/restaurantIndex";
    }

    @GetMapping("/{id}")
    public String Page(@PathVariable int id, Model model) throws RestaurantException, ItemException {
        Restaurant restaurant = restaurantService.viewRestaurant(id);
        model.addAttribute("restaurant", restaurant);

        List<Item> items = restaurantService.getItemsByRestaurantId(id);
        model.addAttribute("items", items);
        // Log the fetched items to inspect them
        for (Item item : items) {
            System.out.println("Item ID: " + item.getid() + ", Item Name: " + item.getItemName() + ", Cost: " + item.getCost());
            // Log other item details as needed
        }
        return "restaurantPage/restaurantIndex"; // or any specific view for a single restaurant
    }
}
