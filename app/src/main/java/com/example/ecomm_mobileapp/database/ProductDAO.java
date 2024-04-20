package com.example.ecomm_mobileapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product... product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM " + ShopDatabase.PRODUCT_TABLE)
    void deleteAll();

//    @Query("SELECT * FROM " + ShopDatabase.PRODUCT_TABLE + " WHERE id = :productId")
//    LiveData<Product> getProductByProductId(int productId);

    @Update
    void updateCurrentUser(Product product);

    @Query("SELECT * FROM " + ShopDatabase.PRODUCT_TABLE + " WHERE id = :productId")
    LiveData<Product> getProductById(int productId);

    @Query("SELECT * FROM " + ShopDatabase.PRODUCT_TABLE)
    LiveData<List<Product>> getAllProducts();


}
