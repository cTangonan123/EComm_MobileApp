package com.example.ecomm_mobileapp.database;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Order;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.User;

import java.util.List;
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

    static ShopDatabase getDatabase(final Context context) {
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

                User admin = new User("admin1", "admin1", true);
                userDAO.insert(admin);
                User testUser1 = new User("testUser1", "testUser1", false);
                userDAO.insert(testUser1);

                ProductDAO productDAO = INSTANCE.productDAO();

                productDAO.deleteAll();

                Product product1 = new Product("Tv", "description of Tv", 2.99);
                productDAO.insert(product1);
                Product product2 = new Product("Microwave", "description of microwave", 1.99);
                productDAO.insert(product2);


// this is not working...perhaps alter POJO or DAO associated with entity declaration
                CartDAO cartDAO = INSTANCE.cartDAO();

                cartDAO.deleteAll();

                Cart cart1 = new Cart(1,1,product1.getProductPrice(),2);
                Cart cart2 = new Cart(1,2,product2.getProductPrice(),1);


                cartDAO.insert(cart1);
                cartDAO.insert(cart2);

                OrderDAO orderDAO = INSTANCE.orderDAO();
                orderDAO.deleteAll();






            });
        }
    };



}
