package com.example.curs_delivery.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.curs_delivery.Model.Product;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * from product")
    List<Product> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Product> products);
}
