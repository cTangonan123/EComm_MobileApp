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

public class ViewCartViewHolder extends RecyclerView.ViewHolder {
    private final TextView productNameTextView;
    private final TextView productPriceTextView;
    private final TextView productQuantityTextView;

    public ViewCartViewHolder(@NonNull View itemView) {
        super(itemView);
        productNameTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_name);
        productPriceTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_price);
        productQuantityTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_quantity);
    }

    public void bind (Product product) {
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(String.format(Locale.US,"$%,.2f", product.getProductPrice()));
    }

    static ViewCartViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcart_recyclerview_items, parent, false);
        return new ViewCartViewHolder(view);
    }
}
