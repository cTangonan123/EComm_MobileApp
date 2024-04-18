package com.example.ecomm_mobileapp.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ecomm_mobileapp.database.entities.User;

public class AdminViewUsersAdapter extends ListAdapter<User, AdminViewUsersViewHolder> {
    public AdminViewUsersAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AdminViewUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdminViewUsersViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewUsersViewHolder holder, int position) {
        User current = getItem(position);
        holder.bind(current);
    }

    public static class ShopDiff extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    }
}
