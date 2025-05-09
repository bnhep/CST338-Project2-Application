package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.database.ApplicationDatabase;

import java.util.Objects;

/**
 * POJO defines the tables in the database. This class will be the table and the fields are the
 * columns. Will be handled in the database for query.
 * @author Brandon Nhep
 * Date: 4/21/25
 */
@Entity(tableName = ApplicationDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private boolean isAdmin = false;


    /**
     * Constructor
     *
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }
}
