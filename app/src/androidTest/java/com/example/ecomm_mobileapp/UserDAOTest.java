package com.example.ecomm_mobileapp;

import androidx.lifecycle.LiveData;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;



public class UserDAOTest {
    private ShopDatabase database;
    private UserDAO userDAO;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ShopDatabase.class)
                .allowMainThreadQueries()
                .build();

        userDAO = database.userDAO();
    }

    @After
    public void cleanup() {
        database.close();
    }

    @Test
    public void insertUser() {
        // Arrange
        User user = new User("testUser", "password", false, "Chase", "Power");

        // Act
        userDAO.insert(user);

        // Assert
        LiveData<User> retrievedUserLiveData = userDAO.getUserByUserId(user.getId());
        User retrievedUser = retrievedUserLiveData.getValue();
        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }

    @Test
    public void updateUser() {
        // Insert a user first
        User user = new User("testUser", "password", false, "John", "Doe");
        userDAO.insert(user);

        // Update the user's details
        user.setFirstName("Jane");
        userDAO.update(user);

        // Retrieve the updated user from the database
        LiveData<User> updatedUserLiveData = userDAO.getUserByUserId(user.getId());
        User updatedUser = updatedUserLiveData.getValue();
        assertNotNull(updatedUser);
        assertEquals("Jane", updatedUser.getFirstName());
    }

    @Test
    public void deleteUser() {
        // Insert a user first
        User user = new User("testUser", "password", false, "Chase", "Power");
        userDAO.insert(user);

        // Delete the user from the database
        userDAO.delete(user);

        // Retrieve the user from the database (should be null after deletion)
        LiveData<User> deletedUserLiveData = userDAO.getUserByUserId(user.getId());
        User deletedUser = deletedUserLiveData.getValue();
        assertNull(deletedUser);
    }
}