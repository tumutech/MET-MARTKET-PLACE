package com.example.met1.Activity;

import java.io.Serializable;

public class productModel implements Serializable {
    String productName,description,productImage,productPrice,discount, description_details,selection,quantity,puserid;
    private int numberinCart;

    public productModel() {
    }

    public productModel(String productName, String description, String productImage, String productPrice, String discount, String description_details, String selection,String aquantity,String apuserid) {
        this.quantity = aquantity;
        this.puserid = apuserid;
        this.productName = productName;
        this.description = description;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.discount = discount;
        this.description_details = description_details;
        this.selection = selection;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPuserid() {
        return puserid;
    }

    public void setPuserid(String puserid) {
        this.puserid = puserid;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setDescription_details(String description_details) {
        this.description_details = description_details;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDescription_details() {
        return description_details;
    }

    public String getSelection() {
        return selection;
    }
    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }

    public int getNumberinCart() {
        return numberinCart;
    }
}
