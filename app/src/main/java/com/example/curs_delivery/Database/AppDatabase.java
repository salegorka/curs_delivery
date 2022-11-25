package com.example.curs_delivery.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.curs_delivery.Converter.Converters;
import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Order;
import com.example.curs_delivery.Model.Product;

@Database(entities = {Product.class, Cart.class, Order.class}, version=1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    public abstract OrderDao orderDao();
}
