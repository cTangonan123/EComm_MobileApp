package com.example.ecomm_mobileapp.viewHolders;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ecomm_mobileapp.database.entities.Product;

public class CheckoutItemsAdapter extends ListAdapter<Product, CheckoutItemsHolder> {
    public CheckoutItemsAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public CheckoutItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CheckoutItemsHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutItemsHolder holder, int position) {
        Product current = getItem(position);
        holder.bind(current);
    }

    public static class ShopDiff extends  DiffUtil.ItemCallback<Product> {
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
