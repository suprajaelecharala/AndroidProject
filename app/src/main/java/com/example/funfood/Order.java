package com.example.funfood;

public class Order {
    private  String Id;
    private String orderId;
    private String customerName;
    private String customerId;
    private String email;
    private String foodName;
    private int qty;
    private String rate;
    private Double total;

    public Order() {
        // Default constructor required for Firebase
    }



    public Order(String orderId, String customerName, String customerId, String email, String foodName, int qty, String rate, Double total) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerId = customerId;
        this.email = email;
        this.foodName = foodName;
        this.qty = qty;
        this.rate = rate;
        this.total = total;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQty() {
        return qty;
    }

    public String getRate() {
        return rate;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }
}

