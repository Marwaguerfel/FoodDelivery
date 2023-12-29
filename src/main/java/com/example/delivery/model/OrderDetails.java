package com.example.delivery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Order_Date")
    private String orderDate;

    @Column(name = "Order_Status")
    private String orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private FoodCart cart;

    @OneToOne(cascade = CascadeType.ALL)
    private Bill bill;

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public FoodCart getCart() {
        return cart;
    }

    public void setCart(FoodCart cart) {
        this.cart = cart;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
