package com.example.curs_delivery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.curs_delivery.Adapter.ProductCategoryAdapter;
import com.example.curs_delivery.Model.ProductCategory;
import com.example.curs_delivery.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Категория 1"));
        productCategoryList.add(new ProductCategory(2, "Категория 2"));
        productCategoryList.add(new ProductCategory(3, "Категория 3"));
        productCategoryList.add(new ProductCategory(4, "Категория 4"));
        setProductCatRecycler(productCategoryList);
    }

    private void setProductCatRecycler(List<ProductCategory> productCategoryList) {
        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new ProductCategoryAdapter(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                return true;
            case R.id.action_cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}