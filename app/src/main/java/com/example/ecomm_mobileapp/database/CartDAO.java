package com.example.ecomm_mobileapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE)
    LiveData<List<Cart>> getAllCart();

    @Query("DELETE FROM " + ShopDatabase.CART_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE userId = :userId")
    LiveData<List<Cart>> getAllCartByUserId(int userId);




//    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE cartId = :cartId")
//    LiveData<Cart> getCartById(int cartId);
}
