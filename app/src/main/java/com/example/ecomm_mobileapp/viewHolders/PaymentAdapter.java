package com.example.ecomm_mobileapp.viewHolders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Payment;


public class PaymentAdapter extends ListAdapter<Payment, PaymentViewHolder> {
    public PaymentAdapter(@NonNull DiffUtil.ItemCallback<Payment> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_recyclerview_items, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment current = getItem(position);
        holder.bind(current);
    }

    public static class PaymentDiff extends DiffUtil.ItemCallback<Payment> {
        @Override
        public boolean areItemsTheSame(@NonNull Payment oldItem, @NonNull Payment newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Payment oldItem, @NonNull Payment newItem) {
            return oldItem.equals(newItem);
        }
    }
}