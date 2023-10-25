package com.example.funfood;

public class CartModel {
    private String id; // Firebase-generated key
    private String food_id;
    private String name;
    private int qty;
    private String rate;
    private Double total;

    public CartModel() {
        // Default constructor required for Firebase
    }

    public CartModel(String id, String food_id, String name, int qty, String rate, Double total) {
        this.id = id;
        this.food_id = food_id;
        this.name = name;
        this.qty = qty;
        this.rate = rate;
        this.total = total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public String getFoodId() {
        return food_id;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public String getRate() {
        return rate;
    }

    public Double getTotal() {
        return total;
    }
}

