package com.example.ecomm_mobileapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
    public void deleteUser() {
        User user = new User("testUser", "password", false, "Chase", "Power");
        userDAO.insert(user);

        userDAO.delete(user);
        User deletedUser = userDAO.getUserByUserId(user.getId()).getValue();
    }
}