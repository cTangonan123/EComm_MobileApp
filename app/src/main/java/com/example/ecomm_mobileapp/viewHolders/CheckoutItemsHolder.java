package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Locale;

public class CheckoutItemsHolder extends RecyclerView.ViewHolder {
    private final TextView productNameTextView;
    private final TextView productPriceTextView;
    private final TextView productQuantityTextView;

    public CheckoutItemsHolder(@NonNull View itemView) {
        super(itemView);
        productNameTextView = itemView.findViewById(R.id.checkout_recyclerview_items_name);
        productQuantityTextView = itemView.findViewById(R.id.checkout_recyclerview_items_quantity);
        productPriceTextView =itemView.findViewById(R.id.checkout_recyclerview_items_price);
    }

    public void bind (Product product) {
        // TODO: update textViews
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(String.format(Locale.US, "$%,.2f", product.getProductPrice()));
        productQuantityTextView.setText(String.format("1"));


    }

    static CheckoutItemsHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_recyclerview_order_items, parent, false);
        return new CheckoutItemsHolder(view);
    }
}
