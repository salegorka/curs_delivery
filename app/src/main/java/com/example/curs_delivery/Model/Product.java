package com.example.curs_delivery.Model;

public class Product {

    // пока без категорий

    Integer id;
    String name;
    String description;
    String price;

    public Product(Integer id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescripion(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

