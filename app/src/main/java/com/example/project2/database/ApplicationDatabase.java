package com.example.project2.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.MainActivity;
import com.example.project2.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Actual Database, if more data needs to be stored use this, create entity and DAO
 */
@Database(entities = {User.class} , version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "applicationDatabase";

    private static volatile ApplicationDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Method to get the database
     */
    static ApplicationDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ApplicationDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            Log.i(MainActivity.TAG, "Database has been created");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.UserDAO();
                dao.deleteAll();
                //Pre-populated admin username
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                //Prepopulated testuser1
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract UserDAO UserDAO();

}
