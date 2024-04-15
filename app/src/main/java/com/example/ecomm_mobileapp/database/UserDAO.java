package com.example.ecomm_mobileapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ecomm_mobileapp.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM " + ShopDatabase.USER_TABLE)
    void deleteAll();

//    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE + " WHERE id = :userId")
//    LiveData<User> getUserByUserId(int userId);

//    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE + " WHERE userName = :userName")
//    LiveData<User> getUserByUserName(int userName);

    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE)
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE + " WHERE userName = :userName")
    LiveData<User> getUserByUserName(String userName);

    @Query("SELECT * from " + ShopDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    //TODO: add more queries as the app is built

}
