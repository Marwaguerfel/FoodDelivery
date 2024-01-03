package com.example.delivery.model;


import jakarta.persistence.*;

@Entity
@Table(name = "BILL")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Bill_Date")
    private String billDate;

    @Column(name = "Total_Cost")
    private Double totalCost;

    @Column(name = "Total_Item")
    private Integer totalItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "id")
    private OrderDetails order;


    public int getid() {
        return id;
    }

    public void setid(int id) {

        this.id = id;
    }

    public String getBillDate() {

        return billDate;
    }

    public void setBillDate(String billDate) {

        this.billDate = billDate;
    }

    public Double getTotalCost() {

        return totalCost;
    }

    public void setTotalCost(Double totalCost) {

        this.totalCost = totalCost;
    }

    public Integer getTotalItem() {

        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {

        this.totalItem = totalItem;
    }

    public OrderDetails getOrder() {

        return order;
    }

    public void setOrder(OrderDetails order) {

        this.order = order;
    }

}
