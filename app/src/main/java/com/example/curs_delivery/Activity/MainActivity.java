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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.curs_delivery.Adapter.ProductAdapter;
import com.example.curs_delivery.Adapter.ProductCategoryAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.ProductDao;
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
    ArrayList<Product> productList;
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
        ProductDao productDao = db.productDao();
        List<Product> productList =  productDao.getAll();

        RecyclerView recyclerViewProduct = findViewById(R.id.product_recycler);
        ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productList);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void LoadMenu() {
        String url = "http://10.0.2.2:80/api/products.php";
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        e.printStackTrace();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SetUpMenu();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.d("TEST", res);

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                        ArrayList<Product> productList = gson.fromJson(res, type);

                        ProductDao productDao = db.productDao();
                        productDao.insertAll(productList);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SetUpMenu();
                            }
                        });
                    }
                });
    }

}