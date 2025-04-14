package com.example.project2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.project2.database.entities.User;



public class ApplicationRepository {

    private final UserDAO userDAO;
    private static volatile ApplicationRepository repository;

    private ApplicationRepository(Application application){
        ApplicationDatabase db = ApplicationDatabase.getDatabase(application);
        this.userDAO = db.UserDAO();
    }

    public static ApplicationRepository getRepository(Application application){
        if (repository == null) {
            synchronized (ApplicationRepository.class) {
                if (repository == null) {
                    repository = new ApplicationRepository(application);
                }
            }
        }
        return repository;
    }


    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUserName(username);
    }

    public void insertUser(User... user){
        ApplicationDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }
}
