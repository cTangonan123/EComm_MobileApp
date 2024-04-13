package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;

public class ShopViewHolder extends RecyclerView.ViewHolder {
    private final TextView productViewItem;
    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        // TODO: change this later for multiple views
        productViewItem = itemView.findViewById(R.id.main_recyclerview_product_name);
    }

    public void bind (String text) {
        productViewItem.setText(text);
    }

    static ShopViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_items, parent, false);
        return new ShopViewHolder(view);
    }
}
