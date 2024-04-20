package com.example.ecomm_mobileapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Order;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: as entities are added to the App, add them to entities parameter below
@Database(entities = {User.class, Product.class, Cart.class, Order.class, Payment.class}, version = 1, exportSchema = false)
public abstract class ShopDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract CartDAO cartDAO();
    public abstract OrderDAO orderDAO();
    public abstract PaymentDAO paymentDAO();
    public abstract ProductDAO productDAO();
    public static final String USER_TABLE = "usertable";
    public static final String PRODUCT_TABLE = "producttable";
    public static final String CART_TABLE = "carttable";
    public static final String ORDER_TABLE = "ordertable";
    public static final String PAYMENT_TABLE = "paymenttable";
    public static final String DATABASE_NAME = "shopdatabase";
    private static volatile ShopDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ShopDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ShopDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShopDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "Database Created!");

            // TODO: add items, shoppingcarts etc to this method
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = INSTANCE.userDAO();

                userDAO.deleteAll();

                User admin = new User("admin2", "admin2", true, "Admin", "AdminLastName");
                userDAO.insert(admin);
                User testUser1 = new User("testuser1", "testuser1", false, "User", "UserLastName");
                userDAO.insert(testUser1);

                //User admin = new User("admin1", "admin1", true);
                //userDAO.insert(admin);
                //User testUser1 = new User("testUser1", "testUser1", false);
                //userDAO.insert(testUser1);

                ProductDAO productDAO = INSTANCE.productDAO();

                productDAO.deleteAll();

                Product product1 = new Product("Tv", "description of Tv", 299.99);
                Product product2 = new Product("Microwave", "description of microwave", 99.99);
                Product product3 = new Product("Monitor", "description of Monitor", 399.99);
                Product product4 = new Product("PC", "description of PC", 1299.99);
                Product product5 = new Product("iPad", "description of iPad", 699.99);
                Product product6 = new Product("iPhone", "description of iPhone", 999.99);
                Product product7 = new Product("Dish Washer", "description of Dish Washer", 399.99);
                Product product8 = new Product("Refrigerator", "description of Refrigerator", 599.99);
                Product product9 = new Product("Charger", "description of Charger", 29.99);
                Product product10 = new Product("Laptop", "description of Laptop", 999.99);
                productDAO.insert(product1);
                productDAO.insert(product2);
                productDAO.insert(product3);
                productDAO.insert(product4);
                productDAO.insert(product5);
                productDAO.insert(product6);
                productDAO.insert(product7);
                productDAO.insert(product8);
                productDAO.insert(product9);
                productDAO.insert(product10);



                CartDAO cartDAO = INSTANCE.cartDAO();

                cartDAO.deleteAll();

                Cart cart1 = new Cart(1,1,product1.getProductPrice(),1);
                Cart cart2 = new Cart(1,2,product2.getProductPrice(),1);


                cartDAO.insert(cart1);
                cartDAO.insert(cart2);

                PaymentDAO paymentDAO = INSTANCE.paymentDAO();

                Payment payment1 = new Payment("admin2", "admin2", "1111111111111111", "111", 1);
                Payment payment2 = new Payment("testuser1", "testuser1", "1111111111111111", "111", 2);
                paymentDAO.insert(payment1);
                paymentDAO.insert(payment2);

                OrderDAO orderDAO = INSTANCE.orderDAO();
                orderDAO.deleteAll();






            });
        }
    };



}
