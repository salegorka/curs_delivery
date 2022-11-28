package com.example.curs_delivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.Model.Order;
import com.example.curs_delivery.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    final LayoutInflater inflater;
    final List<Order> orders;


    public OrderAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = this.orders.get(position);
        String cart_json = order.cart_json;
        Gson gson = new Gson();
        Cart[] cart_arr = gson.fromJson(cart_json, Cart[].class);
        StringBuilder cart_info = new StringBuilder();
        for (Cart cart : cart_arr) {
            cart_info.append(cart.product_name).append(" - ").append(cart.amount).append("; ");
        }
        holder.listView.setText(cart_info);
        long price = 0;
        for (Cart cart : cart_arr) {
            price += (long) cart.price * cart.amount;
        }
        holder.priceView.setText(String.valueOf(price));
        Date order_date = order.order_time;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh-mm", Locale.getDefault());
        String dateTxt = formater.format(order_date);
        holder.dateView.setText(dateTxt);
    }

    @Override
    public int getItemCount() { return orders.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView listView, priceView, dateView;
        ViewHolder(View view) {
            super(view);
            listView = view.findViewById(R.id.orderViewList);
            priceView  = view.findViewById(R.id.orderViewPrice);
            dateView = view.findViewById(R.id.orderViewDate);
        }
    }
}
