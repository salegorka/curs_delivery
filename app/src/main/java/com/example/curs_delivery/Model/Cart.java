package com.example.curs_delivery.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cart {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="product_name")
    public String product_name;

    @ColumnInfo(name="product_id")
    public int product_id;

    @ColumnInfo(name="amount")
    public int amount;

    @ColumnInfo(name="price")
    public int price;

    public Cart(String product_name, int product_id, int amount, int price) {
        this.product_name = product_name;
        this.product_id = product_id;
        this.amount = amount;
        this.price = price;
    }
}
