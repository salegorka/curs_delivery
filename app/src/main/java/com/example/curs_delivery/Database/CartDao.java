package com.example.curs_delivery.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Product;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * from Cart where product_id =:product_id")
    Cart findProduct(int product_id);

    @Query("SELECT * from Cart")
    List<Cart> getItems();

    @Query("DELETE FROM Cart where product_id =:product_id")
    void deleteProduct(int product_id);

    @Query("DELETE FROM Cart")
    void emptyCart();

    @Query("UPDATE Cart SET amount=:amount where product_id=:product_id")
    void updateProductAmount(int amount, int product_id);

    @Insert
    void insertCart(Cart  cart);

    @Update
    void updateCart(Cart cart);
}
