package com.example.ecomm_mobileapp.database.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ecomm_mobileapp.database.ShopDatabase;

import java.util.Objects;

//@Entity(tableName = ShopDatabase.PAYMENT_TABLE,
//        foreignKeys = @ForeignKey(entity = User.class,
//                parentColumns = "id",
//                childColumns = "userId",
//                onDelete = ForeignKey.CASCADE),
//        indices = {@Index("userId")})
@Entity(tableName = ShopDatabase.PAYMENT_TABLE)
public class Payment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String cardNumber;
    private String cardCVV;
    private String firstName;
    private String lastName;

    public Payment(String cardNumber, String cardCVV, String firstName, String lastName) {
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
    //    this.firstName = firstName;
    //    this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && userId == payment.userId && Objects.equals(cardNumber, payment.cardNumber) && Objects.equals(cardCVV, payment.cardCVV) && Objects.equals(firstName, payment.firstName) && Objects.equals(lastName, payment.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, cardNumber, cardCVV, firstName, lastName);
    }
}
