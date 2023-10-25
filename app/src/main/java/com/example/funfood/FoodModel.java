package com.example.funfood;

public class FoodModel {
    String id;
    String name;
    String desc;
    String rate;
    String image_url;

    public FoodModel() {
    }

    public String getId() {
        return id;
    }

    public FoodModel(String id, String name, String desc, String rate, String image_url) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rate = rate;
        this.image_url = image_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
