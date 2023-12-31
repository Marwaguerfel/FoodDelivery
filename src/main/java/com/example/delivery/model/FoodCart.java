package com.example.delivery.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FOOD_CART")
public class FoodCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(targetEntity = Item.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private List<Item> itemList;



    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
