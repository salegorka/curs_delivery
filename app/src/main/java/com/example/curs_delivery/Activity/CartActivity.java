package com.example.curs_delivery.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Adapter.CartAdapter;
import com.example.curs_delivery.App;
import com.example.curs_delivery.Database.AppDatabase;
import com.example.curs_delivery.Database.CartDao;
import com.example.curs_delivery.Database.OrderDao;
import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Order;
import com.example.curs_delivery.R;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    AppDatabase db;

    Button btnDatePicker, btnTimePicker, btnOrder;
    EditText txtDate, txtTime;
    TextView errorTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = App.getInstance().getDatabase();

        btnDatePicker = (Button)findViewById(R.id.buttonDatePickCart);
        btnTimePicker = (Button)findViewById(R.id.buttonTimePickCart);
        btnOrder = (Button)findViewById(R.id.buttonOrder);
        txtDate = (EditText)findViewById(R.id.editTextDateCart);
        txtTime = (EditText)findViewById(R.id.editTextTimeCart);
        errorTxt = (TextView)findViewById(R.id.errorTextCart);
        errorTxt.setVisibility(View.GONE);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == btnDatePicker) {
            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    txtDate.setText(year + "-" + (month + 1) + "-" + day_of_month);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {
            final Calendar calendar = Calendar.getInstance();
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour_of_day, int minute) {
                    txtTime.setText(hour_of_day + "-" + minute);
                }
            }, mHour, mMinute, true);
            timePickerDialog.show();
        }
        if (v == btnOrder) {
            createOrder();
        }
    }

    public void createOrder() {
        String dateTxt = txtDate.getText().toString();
        String timeTxt = txtTime.getText().toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());
        Date orderDate = new Date();
        try {
            orderDate = format.parse(dateTxt + " " + timeTxt);
        } catch (Exception e) {
            errorTxt.setText("Значение полей даты неккоректно. Введите еще раз");
            errorTxt.setVisibility(View.VISIBLE);
            return;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if ((currentDate.getTime() + (60 * 60 * 100)) > orderDate.getTime()){
            errorTxt.setText("Времени слишком мало, чтобы осуществить доставку. Выберите другое время.");
            errorTxt.setVisibility(View.VISIBLE);
            return;
        }

        CartDao cartDao = db.cartDao();
        List<Cart> fullCart = cartDao.getItems();
        Gson gson = new Gson();
        String cart_json = gson.toJson(fullCart);
        Order order = new Order(cart_json, orderDate);
        OrderDao orderDao = db.orderDao();
        orderDao.insert(order);
        cartDao.emptyCart();
        startActivity(new Intent(CartActivity.this, OrdersActivity.class));
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
