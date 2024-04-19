package com.example.ecomm_mobileapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecomm_mobileapp.database.entities.Payment;

import java.util.List;

@Dao
public interface PaymentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Payment... payments);

    @Update
    void update(Payment... payments);

    @Delete
    void delete(Payment payment);

    @Query("SELECT * FROM " + ShopDatabase.PAYMENT_TABLE)
    LiveData<List<Payment>> getAllPayments();

    @Query("DELETE FROM " + ShopDatabase.PAYMENT_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + ShopDatabase.PAYMENT_TABLE + " WHERE userId = :userId")
    LiveData<List<Payment>> getAllPaymentsByUserId(int userId);

//    @Query("SELECT * FROM " + ShopDatabase.PAYMENT_TABLE + " WHERE paymentId = :paymentId")
//    LiveData<Payment> getPaymentById(int paymentId);
}