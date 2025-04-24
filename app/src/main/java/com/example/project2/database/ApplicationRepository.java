package com.example.project2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.project2.database.entities.User;



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

    public static ApplicationRepository getInstance(){
        return repository;
    }


    public void insertUser(User... user){
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUsernameByID(int id) {
        return userDAO.getUsernameByID(id);
    }

    public void setPasswordByUsername(String newPassword, String username) {
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.setPasswordByUsername(newPassword,username);
        });
    }
}
