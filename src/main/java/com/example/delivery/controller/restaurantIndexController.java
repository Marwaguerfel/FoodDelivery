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
import org.springframework.web.bind.annotation.*;

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
    public String Page(@PathVariable int id, Model model) throws RestaurantException, ItemException, CategoryException {
        Restaurant restaurant = restaurantService.viewRestaurant(id);
        model.addAttribute("restaurant", restaurant);
        List<Category> categories = catService.viewAllCategory();
        model.addAttribute("categories", categories);
        List<Item> items = restaurant.getItemList();
        model.addAttribute("items", items);

        // Log the fetched items to inspect them
        System.out.println("Items: " + items); // Log the entire list
        for (Item item : items) {
            System.out.println("Item ID: " + item.getid() + ", Item Name: " + item.getItemName());
        }
        Restaurant restD = restaurantService.viewRestaurant(id);
        model.addAttribute("restaurant", restD);
        return "restaurantPage/restaurantIndex"; // or any specific view for a single restaurant
    }
    @GetMapping("/{restaurantId}/filterItemsByCategory")
    @ResponseBody
    public List<Item> filterItemsByCategory(@PathVariable int restaurantId, @RequestParam("categoryId") int categoryId) throws ItemException {
        return itemService.getItemsByRestaurantAndCategoryId( categoryId,restaurantId);
    }


}
