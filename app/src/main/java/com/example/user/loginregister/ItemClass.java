package com.example.user.loginregister;

/**
 * Created by User on 2017.10.09.
 */

public class ItemClass {
    private int id;
    private String item;
    private String description;
    private double price;

    public ItemClass() {
        id = 0;
        item = "";
        this.description = "";
        price = 0.0;
    }

    public ItemClass(String item, String description, double price) {
        this.item = item;
        this.description = description;
        this.price = price;
    }

    public ItemClass(int id, String item, String description, double price) {
        this.id = id;
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

    public double getPrice() { return price; }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "ID: " + id + " ITEM: " + item + " DESCRIPTION: " + description + " PRICE: " + price;
    }
}


