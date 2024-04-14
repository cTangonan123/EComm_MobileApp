package com.example.ecomm_mobileapp.viewHolders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Locale;

public class ShopViewHolder extends RecyclerView.ViewHolder {
    private final TextView mainRecyclerItemViewName;
    private final TextView mainRecyclerItemViewPrice;
    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        // TODO: change this later for multiple views
        mainRecyclerItemViewName = itemView.findViewById(R.id.main_recyclerview_product_name);
        mainRecyclerItemViewPrice = itemView.findViewById(R.id.main_recyclerview_product_price);

    }



    public void bind (Product product) {
        mainRecyclerItemViewName.setText(product.getProductName());
        mainRecyclerItemViewPrice.setText(String.format(Locale.US,"$%,.2f", product.getProductPrice()));
    }

    static ShopViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_items, parent, false);
        return new ShopViewHolder(view);
    }
}
