package com.example.delivery.controller;

import com.example.delivery.authservice.UserSessionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RestaurantService restService;
    @Autowired
    CategoryService catSer;
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserSessionService userSessionService;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/addrestaurant")
    public String showAddRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "/admin/restaurantManagement/add-restaurant";
    }

    @PostMapping("/addrestaurant")
    public String addRestaurant(@Validated @ModelAttribute Restaurant restaurant) throws RestaurantException {
        restService.addRestaurant(restaurant);
        return "redirect:/admin/restaurants"; // Correct redirection path to the restaurants view
    }
    @GetMapping("/restaurants")
    public String showManageRestaurants(Model model) throws RestaurantException {
        List<Restaurant> restaurants = restService.viewall();
        model.addAttribute("restaurants", restaurants);
        return "admin/restaurantManagement/manage-restaurants";
    }
    @GetMapping("/editRest")
    public String editRestaurantPage(
            Model model,
            @RequestParam int restaurantId
    )throws RestaurantException,ItemException{
        Restaurant restaurant = restService.viewRestaurant(restaurantId);
        model.addAttribute("allItems", itemService.viewAllItems());
        model.addAttribute("restaurant", restaurant);
        return "/admin/restaurantManagement/edit-restaurant";

    }

    @PostMapping("/update")
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) throws RestaurantException {
        Restaurant updatedRestaurant = restService.updateRestaurant(restaurant);
        return "redirect:/admin/restaurantManagement/restaurants";
    }


    @GetMapping("/remove/{restaurantId}")
    public String deleteRes(@PathVariable("restaurantId") Integer restaurantId) throws RestaurantException {
        restService.removeRestaurant(restaurantId);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/view/{restaurantId}")
    public String viewRestaurantDetails(
            @PathVariable("restaurantId") Integer restaurantId,
            Model model
    ) throws RestaurantException {

        Restaurant restaurant = restService.viewRestaurant(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "admin/restaurantManagement/restaurant-detail";

    }
    //item Management section

    @GetMapping("/addItem")
    public String addItem(Model model) throws CategoryException, RestaurantException {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", catSer.viewAllCategory());
        model.addAttribute("restaurants", restService.viewall()); // Assuming catService is your category service
// Assuming catService is your category service
        return "admin/itemManagement/add-item";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("item") Item item, Model model) throws CategoryException, RestaurantException {
        try {
            int categoryId = item.getCategory().getid();
            Category category = catSer.viewCategory(categoryId);
            item.setCategory(category);

            int restaurantId = item.getRestaurant().getid(); // Assuming getId() gives the restaurant ID
            Restaurant restaurant = restService.viewRestaurant(restaurantId);
            item.setRestaurant(restaurant);

            Item newItem = itemService.addItem(item);

            return "redirect:/admin/allItem";
        } catch (CategoryException | RestaurantException e) {
            // Handle exceptions, for example, by redirecting to the add page with an error message
            model.addAttribute("error", "Error adding item: " + e.getMessage());
            model.addAttribute("item", item); // To repopulate the form with the submitted data
            model.addAttribute("categories", catSer.viewAllCategory());
            model.addAttribute("restaurants", restService.viewall());
            return "admin/itemManagement/add-ite";
        } catch (ItemException e) {
            throw new RuntimeException(e);
        }
    }




    @GetMapping("/editItem")
    public String edit(Model model,
                       RedirectAttributes attributes,
                       @RequestParam int id
    ) throws CategoryException,ItemException {
        Item item=itemService.viewItem(id);
        model.addAttribute("item", item);
        model.addAttribute("categories", catSer.viewAllCategory()); // Assuming catService is your category service
        return "edit-item" ;
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute("item") Item item) throws ItemException {
        Item updatedItem = itemService.updateItem(item);
        return "redirect:admin/itemManagement/listItem";
    }


    @GetMapping("/viewItem/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable("itemId") Integer itemId) throws ItemException{
        Item item = itemService.viewItem(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.ACCEPTED);
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") int id) throws ItemException{
        Item removedItem = itemService.removeItem(id);
        return "redirect:/admin/allItem";
    }


    @GetMapping("/allItem")
    public String getAllItems( Model model) throws ItemException{
        List<Item> items = itemService.viewAllItems();
        model.addAttribute("items",items);
        return "admin/itemManagement/list-item";
    }



    //category
    @GetMapping("/addCategory")
    public String addCategory(Model model)  {
        model.addAttribute("category", new Category());
      // Assuming catService is your category service
// Assuming catService is your category service
        return "admin/categories/add-category";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") Category category,Model model) throws CategoryException {
        Category newCategory = catSer.addCategory(category);
        return "redirect:/admin/listCategories";
    }
    @GetMapping("/listCategories")
    public String getAllCategories(Model model) throws CategoryException {
        List<Category> categories = catSer.viewAllCategory();
        model.addAttribute("categories", categories);
        return "admin/categories/list-categories";
    }
    @GetMapping("/edit")
    public String getEditPage(
            Model model,
            RedirectAttributes attributes,
            @RequestParam int categoryId
    ) {
        String page = null;
        try {
            Category category = catSer.viewCategory(categoryId);
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
            Category updatedCategory = catSer.updateCategory(category);
            attributes.addAttribute("message", "Category with id: '" + updatedCategory.getid() + "' is updated successfully!");
        } catch (CategoryException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:viewall";
    }


    @GetMapping("/view/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") int categoryId) throws CategoryException{
        Category category = catSer.viewCategory(categoryId);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }


    @GetMapping("/remove/{id}")
    public String removeCategory(
            @PathVariable int id,
            RedirectAttributes attributes
    ) {
        try {
            catSer.removeCategory(id);
            attributes.addAttribute("message", "Category with Id: '" + id + "' is removed successfully!");
        } catch (CategoryException  e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/viewall";
    }


}
