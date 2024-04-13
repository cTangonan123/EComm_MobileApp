package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;

public class ShopViewHolder extends RecyclerView.ViewHolder  {
    private final TextView shopItemView;

    private ShopViewHolder(View itemView) {
        super(itemView);
//        shopItemView = itemView.findViewById(R.id.recyclerItemTextview);
    }

    public void bind(String text) {
        shopItemView.setText(text);
    }

    static ShopViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recyclerview_item, parent, false);
        return new ShopViewHolder(view);
    }
}
