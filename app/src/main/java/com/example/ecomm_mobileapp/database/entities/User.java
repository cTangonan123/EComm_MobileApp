package com.example.ecomm_mobileapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String userName;
    @NonNull
    private String password;

    @NonNull
    private boolean isAdmin;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    // TODO: Look up implementing a foreign key
//    @ForeignKey()
//    private int paymentId;


    // Constructor

    public User(@NonNull String userName, @NonNull String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }


    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
