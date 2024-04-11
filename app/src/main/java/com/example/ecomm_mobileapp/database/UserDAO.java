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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("SELECT * FROM user_table WHERE userName = :userName")
    LiveData<User> getUserByUserName(int userName);

    //TODO: add more queries as the app is built

}
