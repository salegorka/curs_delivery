package com.example.curs_delivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Model.Cart;
import com.example.curs_delivery.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    final LayoutInflater inflater;
    final List<Cart> carts;
    private ItemClickListener myItemClickListener;

    public CartAdapter(Context context, List<Cart> carts, ItemClickListener itemClickListener) {
        this.carts = carts;
        this.inflater = LayoutInflater.from(context);
        this.myItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.productNameView.setText(cart.product_name);
        holder.amountView.setText(cart.amount);
        holder.priceView.setText(String.format("%d руб." ,cart.price));
        holder.buttonDelete.setOnClickListener(view -> {
            myItemClickListener.onItemClick(carts.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public interface ItemClickListener {
        void onItemClick(Cart cart);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView productNameView, amountView, priceView;
        final Button buttonDelete;
        ViewHolder(View view) {
            super(view);
            productNameView = view.findViewById(R.id.textViewProductName);
            amountView = view.findViewById(R.id.textViewAmount);
            priceView = view.findViewById(R.id.textViewPrice);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }

    }
}
