package com.example.project2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project2.database.entities.User;

import java.util.List;

/**
 * Class that manages the user DAO's Querying and other data operations. Allows access to the data
 * within other classes.
 * @author Brandon Nhep
 * Date: 4/21/25
 */
public class ApplicationRepository {

    private final UserDAO userDAO;
    private static volatile ApplicationRepository repository;

    private ApplicationRepository(Application application){
        ApplicationDatabase db = ApplicationDatabase.getDatabase(application);
        this.userDAO = db.UserDAO();
    }

    public static void init(Application application){
        if (repository == null) {
            synchronized (ApplicationRepository.class) {
                if (repository == null) {
                    repository = new ApplicationRepository(application);
                }
            }
        }
    }

    /**
     * returns the instance of the repository
     * @return
     */
    public static ApplicationRepository getInstance(){
        return repository;
    }

    /**
     * inserts a user to the userDAO
     * @param user
     */
    public void insertUser(User... user){
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    /**
     * Returns the row based on the username
     * @param username
     * @return
     */
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    /**
     * Returns the username by passing in the ID
     * @param id
     * @return
     */
    public LiveData<User> getUsernameByID(int id) {
        return userDAO.getUsernameByID(id);
    }

    /**
     * Takes a password and username in parameter to change the userDAO password based of the
     * the username entered.
     * @param newPassword
     * @param username
     */
    public void setPasswordByUsername(String newPassword, String username) {
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.setPasswordByUsername(newPassword,username);
        });
    }

    /**
     * Takes a username and another username to change the userDAO username based of the first
     * received in the parameter
     * @param newUsername
     * @param username
     */
    public void setUsernameByUsername(String newUsername, String username) {
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.setUsernameByUsername(newUsername,username);
        });
    }

    /**
     * returns all the user's in the DAO as a live data
     * @return
     */
    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    /**
     * Delete a certain user based off the user being passed
     * @param user
     */
    public void deleteUser(User user){
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.delete(user);
        });
    }

}
