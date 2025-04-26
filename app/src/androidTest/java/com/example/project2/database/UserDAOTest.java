package com.example.project2.database;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;


import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;


import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class UserDAOTest {

    private ApplicationDatabase db;
    private UserDAO userDAO;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).allowMainThreadQueries().build();
        userDAO = db.UserDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsertAndGetUser() {

        String username = "test username";
        String password = "test password";
        User user = new User(username, password);

        userDAO.insert(user);

        User userEntered = userDAO.testGetUserByUserName(username);

        assertNotNull(userEntered);
        assertEquals(user.getUsername(),userEntered.getUsername());

        //Testing multiple users
        User user1 = new User("testuser1", "testuser1");
        User user2 = new User("testuser2", "testuser1");
        User user3 = new User ("testuser3", "testuser1");
        userDAO.insert(user1);
        userDAO.insert(user2);
        userDAO.insert(user3);

        List<User> allUsers = userDAO.testGetAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
        assertTrue(allUsers.containsAll(userDAO.testGetAllUsers()));

    }

    @Test
    public void testDelete() {
        List<User> allUsers;
        User user1 = new User("testuser1", "testuser1");
        User user2 = new User("testuser2", "testuser1");
        User user3 = new User ("testuser3", "testuser1");
        userDAO.insert(user1);
        userDAO.insert(user2);
        userDAO.insert(user3);

        allUsers = userDAO.testGetAllUsers();
        assertFalse(allUsers.isEmpty());

        userDAO.deleteAll();
        allUsers = userDAO.testGetAllUsers();
        assertTrue(allUsers.isEmpty());
        assertFalse(allUsers.contains(userDAO.testGetUserByUserName("testuser2")));

        User user4 = new User("testuser4", "testuser4");

        userDAO.insert(user4);

        userDAO.delete(user4);

        int id = user4.getId();

        User deletedUser = userDAO.getUserById(id);
        assertNull(deletedUser);
    }

    @Test
    public void testUpdate() {
        User user1 = new User("testuser1", "testuser1");
        userDAO.insert(user1);

        User insertedUser = userDAO.testGetUserByUserName("testuser1");
        int id = insertedUser.getId();

        insertedUser.setPassword("newpassword");
        userDAO.update(insertedUser);

        User updatedUser = userDAO.getUserById(id);

        assertEquals("newpassword", updatedUser.getPassword());

    }

}