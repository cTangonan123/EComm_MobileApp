package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Locale;

public class AdminViewsItemsViewHolder extends RecyclerView.ViewHolder{

    private final TextView productNameTextView;
    private final TextView productPriceTextView;
    private final TextView productDescriptionTextView;

    private final Button deleteItemButton;

    public AdminViewsItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        productNameTextView = itemView.findViewById(R.id.admin_view_items_recyclerview_product_name);
        productPriceTextView = itemView.findViewById(R.id.admin_view_items_recyclerview_product_price);
        productDescriptionTextView = itemView.findViewById(R.id.admin_view_items_recyclerview_product_description);
        deleteItemButton = itemView.findViewById(R.id.admin_view_items_recyclerview_product_btn_delete);

    }

    public void bind (Product product) {
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(String.format(Locale.US,"$%,.2f", product.getProductPrice()));
        productDescriptionTextView.setText(product.getProductDescription());
    }

    static AdminViewsItemsViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_items_recyclerview_items, parent, false);

        return new AdminViewsItemsViewHolder(view);
    }

}
