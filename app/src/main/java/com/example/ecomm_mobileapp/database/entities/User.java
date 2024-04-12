package com.example.ecomm_mobileapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "usertable")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;



    private String userName;


    private String password;



    private boolean isAdmin;



    private String firstName;


    private String lastName;

    // TODO: Look up implementing a foreign key
//    @ForeignKey()
//    private int paymentId;


    // Constructor

    public User( String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        // TODO: add firstname and last name constructor as well
        this.firstName = "fName"; // these are place holders, will add later
        this.lastName = "lName";
    }


    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName) {
        this.lastName = lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, isAdmin, firstName, lastName);
    }
}
