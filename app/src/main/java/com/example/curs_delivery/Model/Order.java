package com.example.curs_delivery.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Order {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="cart_json")
    public String cart_json;

    @ColumnInfo(name="order_time")
    public Date order_time;

    public Order (String cart_json, Date order_time) {
        this.cart_json = cart_json;
        this.order_time = order_time;
    }
}
