package com.example.ecomm_mobileapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecomm_mobileapp.database.entities.Order;

import java.util.List;

@Dao

public interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Order... orders);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM " + ShopDatabase.ORDER_TABLE)
    LiveData<List<Order>> getAllOrders();

    @Query("DELETE FROM " + ShopDatabase.ORDER_TABLE)
    void deleteAll();

//    @Query("SELECT * FROM " + ShopDatabase.ORDER_TABLE + " WHERE orderId = :orderId")
//    LiveData<Order> getOrderById(int orderId);

}
