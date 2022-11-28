package com.example.curs_delivery.Activity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.curs_delivery.Adapter.ProductAdapter;
import com.example.curs_delivery.Adapter.ProductCategoryAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.CartDao;
import com.example.curs_delivery.Database.ProductDao;
import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Product;
import com.example.curs_delivery.Model.ProductCategory;
import com.example.curs_delivery.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler;
    List<Product> productList;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        db = App.getInstance().getDatabase();

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Категория 1"));
        productCategoryList.add(new ProductCategory(2, "Категория 2"));
        productCategoryList.add(new ProductCategory(3, "Категория 3"));
        productCategoryList.add(new ProductCategory(4, "Категория 4"));
        setProductCatRecycler(productCategoryList);

        LoadMenu();
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
            case R.id.action_orders:
                startActivity(new Intent(MainActivity.this, OrdersActivity.class));
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

    private void SetUpMenu() {
        RecyclerView recyclerViewProduct = findViewById(R.id.product_recycler);
        ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, this.productList, new ProductAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                clickOnMenuItem(product);
            }
        });
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void clickOnMenuItem(Product product) {
        // Добавляем продукт в корзину, и показываем сообщение
        CartDao cartDao = db.cartDao();
        List<Cart> currentCart = cartDao.getItems();

        Cart currentProduct = null;

        Log.d("Test", currentCart.toString());
        Log.d("Test", product.name);
        Log.d("Test", String.valueOf(product.id));
        if (currentCart.size() != 0) {
            for (int i = 0; i < currentCart.size(); i++) {
                Log.d("Test", String.valueOf(currentCart.get(i).product_id));
                Log.d("Test", String.valueOf(product.id));
                if (currentCart.get(i).product_id == product.id) {
                    currentProduct = currentCart.get(i);
                }
            }
        }
        if (currentProduct == null) {
            // новый продукт, вставляем
            Log.d("Test", "new");
            currentProduct = new Cart(product.name, product.id, 1, product.price);
            cartDao.insertCart(currentProduct);
        } else {
            // старый продукт, обновляем
            Log.d("Test", currentProduct.product_name);
            currentProduct.amount += 1;
            cartDao.updateCart(currentProduct);
        }
        Toast.makeText(getApplicationContext(), "Продукт добавлен в корзину!", Toast.LENGTH_LONG).show();
    }

    private void LoadMenu() {
        ProductDao productDao = db.productDao();
        int numProduct = productDao.countProducts();
        List<Product> productList = new ArrayList<Product>();
        if (numProduct < 5) {
            //Бд пуста, добавляем данные - Это заглушка
            Product product1 = new Product(1, "Суп Борщ", "Суп Борщ", 100);
            productList.add(product1);
            Product product2 = new Product(2, "Пюре картофельное", "Пюре картофельное", 60);
            productList.add(product2);
            Product product3 = new Product(3, "Компот", "Компот из сухофруктов", 20);
            productList.add(product3);
            Product product4 = new Product(4, "Котлета по киевски", "Котлета куриная с маслом", 70);
            productList.add(product4);
            Product product5 = new Product(5 ,"Гречка", "Вареная гречка", 15);
            productList.add(product5);
            productDao.insertAll(productList);
        } else {
            productList = productDao.getAll();
        }
        this.productList = productList;
        SetUpMenu();
    }


}