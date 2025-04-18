package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.User;

import java.util.List;

/**
 * Queries to run in the database
 * Actions to be performed in our database
 */
@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM " + ApplicationDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE id == :id")
    LiveData<User> getUsernameByID(int id);
}
