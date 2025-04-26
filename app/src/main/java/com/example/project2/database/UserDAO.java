package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.database.entities.User;

import java.util.List;

/**
 * Interface class to define methods for query, insert, update and deleting a user from the
 * userTable entity.
 *
 * @author Brandon Nhep
 * Date: 4/21/25
 */
@Dao
public interface UserDAO {

    /**
     * Inserts a user to the table
     *
     * @param user
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... user);

    /**
     * deletes a user from the table
     *
     * @param user
     */
    @Delete
    void delete(User user);

    @Update
    void update(User user);

    /**
     * Deletes all users from the table
     */
    @Query("DELETE FROM " + ApplicationDatabase.USER_TABLE)
    void deleteAll();


    /**
     * Uses LiveData as a way observe and getAllUsers()
     *
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " ORDER BY username")
    List<User> testGetAllUsers();

    /**
     * Uses live data as a way to observe and get users by their username
     *
     * @param username
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    /**
     * Used to receive user for the tests to check if database was properly instantiated.
     *
     * @param username
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE username = :username LIMIT 1")
    User testGetUserByUserName(String username);


    /**
     * Uses Live data as a way to observe and get the user by their ID
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE id == :id")
    LiveData<User> getUsernameByID(int id);

    /**
     * Sets the password based off the username
     *
     * @param newPassword
     * @param username
     */
    @Query("UPDATE userTable SET password = :newPassword WHERE username = :username")
    void setPasswordByUsername(String newPassword, String username);

    /**
     * sets the username based off the username
     *
     * @param newUsername
     * @param username
     */
    @Query("UPDATE userTable SET username = :newUsername WHERE username = :username")
    void setUsernameByUsername(String newUsername, String username);

    @Query("SELECT * FROM " + ApplicationDatabase.USER_TABLE + " WHERE id = :id")
    User getUserById(int id);
}
