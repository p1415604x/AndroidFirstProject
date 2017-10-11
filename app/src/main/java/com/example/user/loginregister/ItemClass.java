package com.example.user.loginregister;

/**
 * Created by User on 2017.10.09.
 */

public class ItemClass {
    private String item;
    private String description;
    private double price;

    public ItemClass() {
        item = "";
        this.description = "";
        price = 0.0;
    }

    public ItemClass(String item, String description, double price) {
        this.item = item;
        this.description = description;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


