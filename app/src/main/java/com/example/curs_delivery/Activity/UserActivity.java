package com.example.curs_delivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curs_delivery.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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
                startActivity(new Intent(UserActivity.this, MainActivity.class));
                return true;
            case R.id.action_calendar:
                startActivity(new Intent(UserActivity.this, CalendarActivity.class));
                return true;
            case R.id.action_cart:
                startActivity(new Intent(UserActivity.this, CartActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
