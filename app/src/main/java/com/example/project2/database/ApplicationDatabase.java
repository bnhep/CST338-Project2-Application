package com.example.project2.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.Ability;
import com.example.project2.MainActivity;
import com.example.project2.creatures.ElectricRat;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.database.entities.User;
import com.example.project2.utilities.Converters;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Actual Database, if more data needs to be stored use this, create entity and DAO
 */
@Database(entities = {User.class, CreatureEntity.class, AbilityEntity.class} , version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    public static final String USER_TABLE = "userTable";

    public static final String CREATURE_TABLE = "creatureTable";
    public static final String ABILITY_TABLE = "abilityTable";

    private static final String DATABASE_NAME = "applicationDatabase";

    private static volatile ApplicationDatabase INSTANCE;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    /**
     * Singleton Method to get the database, makes sure only one instance of a database is running
     */
    public static ApplicationDatabase getDatabase(final Context context){
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

    /**
     * On build, this will instantiate the database with default account, maybe monsters in the
     * future possibly.
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            Log.i(MainActivity.TAG, "Database has been created");
            databaseWriteExecutor.execute(() -> {
                //add users
                UserDAO dao = INSTANCE.UserDAO();
                dao.deleteAll();
                //Pre-populated admin username
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                //Pre-populated testuser1
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract UserDAO UserDAO();
    public abstract AbilityDAO AbilityDAO();
    public abstract CreatureDAO CreatureDAO();

}
