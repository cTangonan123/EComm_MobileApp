package com.example.ecomm_mobileapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ecomm_mobileapp.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: as entities are added to the App, add them to entities parameter below
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class ShopDatabase extends RoomDatabase {

    public static final String USER_TABLE = "user_table";
    private static final String DATABASE_NAME = "shop_database";
    private static volatile ShopDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ShopDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShopDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ShopDatabase.class, DATABASE_NAME)
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

            // This is for hardcoding values into our database
            // TODO: add items, shoppingcarts etc to this method
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = INSTANCE.userDAO();
                userDAO.deleteAll();
                User admin = new User("admin1", "admin1", true);
                userDAO.insert(admin);

                User testUser1 = new User("testUser1", "testUser1", false);
                userDAO.insert(testUser1);
            });
        }
    };

    public abstract UserDAO userDAO();
}
