package com.example.curs_delivery.Model;

public class Product {

    // пока без категорий

    public Integer id;
    public String name;
    public String description;
    public Integer price;

    public Product(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}

