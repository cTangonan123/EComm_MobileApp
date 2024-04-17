package com.example.ecomm_mobileapp.viewHolders;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.ProductViewActivity;
import com.example.ecomm_mobileapp.ViewCartActivity;
import com.example.ecomm_mobileapp.database.comboHolders.ProductAndCart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.List;

public class ShopAdapter extends ListAdapter<Product, ShopViewHolder> {
    public ShopAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return ShopViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product current = getItem(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), v.getContext().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = ProductViewActivity.productViewActivityIntentFactory(v.getContext(), current.getId());
                v.getContext().startActivity(intent);
            }
        });

        holder.bind(current);
    }

    public static class ShopDiff extends DiffUtil.ItemCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    }
}
