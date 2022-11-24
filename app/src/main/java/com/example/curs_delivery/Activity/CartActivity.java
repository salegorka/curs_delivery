package com.example.curs_delivery.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Adapter.CartAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.CartDao;
import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.R;

import java.util.Calendar;
import java.util.List;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    AppDatabase db;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = App.getInstance().getDatabase();

        btnDatePicker = (Button)findViewById(R.id.buttonDatePickCart);
        btnTimePicker = (Button)findViewById(R.id.buttonTimePickCart);
        txtDate = (EditText)findViewById(R.id.editTextDateCart);
        txtTime = (EditText)findViewById(R.id.editTextTimeCart);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == btnDatePicker) {
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    txtDate.setText(year + "-" + (month + 1) + "-" + day_of_month);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        /*
        if (v == btnTimePicker) {

        }
         */
    }


    @Override
    protected void onResume() {
        super.onResume();
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
        RecyclerView cartRecycler = findViewById(R.id.cart_recycler);
        int amountCart = cartDao.countCarts();
        if (amountCart == 0) {
            TextView textViewHeader = (TextView)findViewById(R.id.textViewHeader);
            textViewHeader.setText("Корзина пуста");
            cartRecycler.setVisibility(View.GONE);
            ConstraintLayout cartControl = (ConstraintLayout)findViewById(R.id.cartControlLayout);
            cartControl.setVisibility(View.GONE);
        } else {
            cartRecycler.setVisibility(View.VISIBLE);
            List<Cart> carts = cartDao.getItems();
            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, carts, new CartAdapter.ItemClickListener() {
                @Override
                public void onItemClick(Cart cart, int op) { clickOnCartItem(cart, op);}
            });
            cartRecycler.setAdapter(cartAdapter);
            setUpFullPriceInfo();
        }
    }

    private void clickOnCartItem(Cart cart, int op) {
        CartDao cartDao = db.cartDao();
        switch(op) {
            case CartAdapter.ItemClickListener.OP_DEL:
                cartDao.deleteProduct(cart.product_id);
                setUpCart();
                break;
            case CartAdapter.ItemClickListener.OP_MINUS:
                if (cart.amount > 1) {
                    cartDao.updateProductAmount(cart.amount - 1, cart.product_id);
                    setUpCart();
                } else {
                    cartDao.deleteProduct(cart.product_id);
                    setUpCart();
                }
                break;
            case CartAdapter.ItemClickListener.OP_PLUS:
                cartDao.updateProductAmount(cart.amount + 1, cart.product_id);
                setUpCart();
                break;
        }
    }

    private void setUpFullPriceInfo() {
        CartDao cartDao = db.cartDao();
        List<Cart> carts = cartDao.getItems();
        int fullPrice = 0;
        for (Cart cart : carts ) {
            fullPrice = (cart.price * cart.amount) + fullPrice;
        }
        ConstraintLayout cartControl = (ConstraintLayout)findViewById(R.id.cartControlLayout);
        cartControl.setVisibility(View.VISIBLE);
        TextView fullPriceView = findViewById(R.id.allFullPriceView);
        fullPriceView.setText(String.format("%d руб.", fullPrice));
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
