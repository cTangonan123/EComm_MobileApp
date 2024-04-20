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

    @Query("DELETE FROM " + ShopDatabase.CART_TABLE + " WHERE userId = :userId")
    void deleteAllCartsByUserId(int userId);

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE)
    LiveData<List<Cart>> getAllCart();

    @Query("DELETE FROM " + ShopDatabase.CART_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE userId = :userId")
    LiveData<List<Cart>> getAllCartByUserId(int userId);

    // Link to usage: https://www.quora.com/How-do-you-write-a-query-to-get-all-the-rows-from-one-table-and-only-those-that-have-matching-values-in-another-table
    @Query("SELECT * FROM " + ShopDatabase.PRODUCT_TABLE + " INNER JOIN " + ShopDatabase.CART_TABLE  + " ON " + ShopDatabase.CART_TABLE + ".productId = " + ShopDatabase.PRODUCT_TABLE + ".id and " + ShopDatabase.CART_TABLE + ".userId = :userId" )
    LiveData<List<Product>> getAllProductsInCartByUserId(int userId);

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE productId = :productId and userId = :userId")
    LiveData<Cart> getCartFromProductIdAndUserId(int productId,int userId);




//    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE cartId = :cartId")
//    LiveData<Cart> getCartById(int cartId);
}
