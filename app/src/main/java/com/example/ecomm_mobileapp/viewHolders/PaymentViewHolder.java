package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Payment;

public class PaymentViewHolder extends RecyclerView.ViewHolder {
    private final TextView paymentFirstNameTextView;
    private final TextView paymentLastNameTextView;
    private final TextView paymentCardNumberTextView;

    public PaymentViewHolder(View paymentView) {
        super(paymentView);
        paymentFirstNameTextView = paymentView.findViewById(R.id.payment_recyclerview_first_name);
        paymentLastNameTextView = paymentView.findViewById(R.id.payment_recyclerview_last_name);
        paymentCardNumberTextView = paymentView.findViewById(R.id.payment_recyclerview_card_number);
    }

    public void bind(Payment payment) {
        paymentFirstNameTextView.setText(payment.getFirstName());
        paymentLastNameTextView.setText(payment.getLastName());
        paymentCardNumberTextView.setText(payment.getCardNumber());
    }

    static PaymentViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_recyclerview_items, parent, false);
        return new PaymentViewHolder(view);
        }
    }
    
