package com.example.curs_delivery.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM `order` ORDER BY order_time")
    List<Order> getItems();

    @Insert
    void insert(Order order);
}
