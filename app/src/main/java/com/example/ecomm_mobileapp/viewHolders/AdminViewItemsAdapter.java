package com.example.ecomm_mobileapp.viewHolders;


import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.database.entities.Product;


public class AdminViewItemsAdapter extends ListAdapter<Product, AdminViewsItemsViewHolder> {

    public AdminViewItemsAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AdminViewsItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdminViewsItemsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewsItemsViewHolder holder, int position) {
        Product current = getItem(position);
        Log.i(MainActivity.TAG, "position: " + position);
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
