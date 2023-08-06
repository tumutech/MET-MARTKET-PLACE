package com.example.met1.Domain;

import java.io.Serializable;

public class ProductDomain implements Serializable {
    private String title;
    private String description;
    private String picUrl;
    private double price;
    private String location;
    private double discount;
    private int numberinCart;

    public ProductDomain(String title, String description, String picUrl, double price, String location, double discount) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.location = location;
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }
}
