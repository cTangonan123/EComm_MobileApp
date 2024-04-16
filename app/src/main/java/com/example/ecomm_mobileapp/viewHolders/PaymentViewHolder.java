package com.example.ecomm_mobileapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Payment;

public class PaymentViewHolder extends RecyclerView.ViewHolder {
    private final TextView paymentFirstNameTextView;
    private final TextView paymentLastNameTextView;
    private final TextView paymentCardNumberTextView;

    public PaymentViewHolder(@NonNull View itemView) {
        super(itemView);
        paymentFirstNameTextView = itemView.findViewById(R.id.payment_recyclerview_first_name);
        paymentLastNameTextView = itemView.findViewById(R.id.payment_recyclerview_last_name);
        paymentCardNumberTextView = itemView.findViewById(R.id.payment_recyclerview_card_number);
    }

    public void bind(Payment payment) {
        paymentFirstNameTextView.setText(payment.getFirstName());
        paymentLastNameTextView.setText(payment.getLastName());
        paymentCardNumberTextView.setText(payment.getCardNumber());
    }
}
