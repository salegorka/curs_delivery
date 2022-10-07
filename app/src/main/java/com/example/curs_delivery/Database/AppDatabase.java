package com.example.curs_delivery.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Product;

@Database(entities = {Product.class, Cart.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
}
