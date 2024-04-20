package com.example.ecomm_mobileapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
        User user = new User("testUser", "password", false, "Chase", "Power");
        userDAO.insert(user);

        LiveData<User> retrievedUserLiveData = userDAO.getUserByUserId(user.getId());
        User retrievedUser = retrievedUserLiveData.getValue();
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