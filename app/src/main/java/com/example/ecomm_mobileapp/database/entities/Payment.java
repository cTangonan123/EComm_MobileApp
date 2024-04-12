package com.example.ecomm_mobileapp.database.entities;

import androidx.annotation.NonNull;

@Entity(tableName = "payment_table")
public class Payment {
    @PrimaryKey(autoGenerate = true)
    private int paymentID;

    @NonNull
    private String cardNumber;
    @NonNull
    private String cardCVV;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    public Payment(@NonNull String cardNumber, @NonNull String cardCVV) {
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    @NonNull
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(@NonNull String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @NonNull
    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(@NonNull String cardCVV) {
        this.cardCVV = cardCVV;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }
}
