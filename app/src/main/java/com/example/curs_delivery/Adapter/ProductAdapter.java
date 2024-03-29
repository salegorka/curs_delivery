package com.example.curs_delivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.curs_delivery.Model.Product;
import com.example.curs_delivery.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    final LayoutInflater inflater;
    final List<Product> products;
    private ItemClickListener myItemClickListener;

    public ProductAdapter(Context context, List<Product> products, ItemClickListener itemClickListener) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.myItemClickListener = itemClickListener;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameView.setText(product.name);
        holder.descriptionView.setText(product.description);
        holder.priceView.setText(String.format("%d руб.", product.price));
        holder.buttonAdd.setOnClickListener(view -> {
            myItemClickListener.onItemClick(products.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface ItemClickListener {
        void onItemClick(Product product);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, descriptionView, priceView;
        final Button buttonAdd;
        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.product_name);
            descriptionView = view.findViewById(R.id.product_description);
            priceView = view.findViewById((R.id.product_price));
            buttonAdd = view.findViewById(R.id.button_add_to_cart);
        }
    }

}
