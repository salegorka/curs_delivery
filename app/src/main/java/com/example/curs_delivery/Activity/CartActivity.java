package com.example.curs_delivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Adapter.CartAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.CartDao;
import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.R;

import java.util.List;


public class CartActivity extends AppCompatActivity {
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = App.getInstance().getDatabase();

        setUpCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpCart() {
        CartDao cartDao = db.cartDao();
        int amountCart = cartDao.countCarts();
        if (amountCart == 0) {
            TextView textViewHeader = (TextView)findViewById(R.id.textViewHeader);
            textViewHeader.setText("Корзина пуста");
        } else {
            List<Cart> carts = cartDao.getItems();
            RecyclerView cartRecycler = findViewById(R.id.cart_recycler);
            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, carts, new CartAdapter.ItemClickListener() {
                @Override
                public void onItemClick(Cart cart) { clickOnCartItem(cart);}
            });
            cartRecycler.setAdapter(cartAdapter);
        }
    }

    private void clickOnCartItem(Cart cart) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                return true;
            case R.id.action_orders:
                startActivity(new Intent(CartActivity.this, OrdersActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(CartActivity.this, UserActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
