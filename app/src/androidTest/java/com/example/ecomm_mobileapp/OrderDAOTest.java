package com.example.ecomm_mobileapp;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



import com.example.ecomm_mobileapp.database.OrderDAO;
import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.entities.Order;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class OrderDAOTest {
    private ShopDatabase database;
    private OrderDAO orderDAO;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ShopDatabase.class)
                .allowMainThreadQueries()
                .build();

        orderDAO = database.orderDAO();
    }

    @After
    public void cleanup() {
        database.close();
    }

    @Test
    public void deleteAllOrders() throws InterruptedException {

        Order order1 = new Order(100.0);
        Order order2 = new Order(200.0);
        orderDAO.insert(order1, order2);

        orderDAO.deleteAll();

        List<Order> orders = LiveDataTestUtil.getValue(orderDAO.getAllOrders());

        assertTrue(orders.isEmpty());
    }

}