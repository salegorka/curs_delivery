package com.example.curs_delivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.curs_delivery.Adapter.OrderAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.OrderDao;
import com.example.curs_delivery.Model.Order;
import com.example.curs_delivery.R;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Intent intent = getIntent();
        db = App.getInstance().getDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView ordersRecycler = (RecyclerView)findViewById(R.id.ordersRecycler);
        OrderDao orderDao = db.orderDao();
        List<Order> orders = orderDao.getItems();
        OrderAdapter ordersAdapter = new OrderAdapter(OrdersActivity.this, orders);
        ordersRecycler.setAdapter(ordersAdapter);
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
            case R.id.action_menu:
                startActivity(new Intent(OrdersActivity.this, MainActivity.class));
                return true;
            case R.id.action_cart:
                startActivity(new Intent(OrdersActivity.this, CartActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(OrdersActivity.this, UserActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}