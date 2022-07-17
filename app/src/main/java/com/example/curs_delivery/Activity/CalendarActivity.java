package com.example.curs_delivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curs_delivery.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();

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
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                return true;
            case R.id.action_cart:
                startActivity(new Intent(CalendarActivity.this, CartActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(CalendarActivity.this, UserActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}