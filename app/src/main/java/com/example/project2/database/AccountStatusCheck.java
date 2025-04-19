package com.example.project2.database;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author Brandon Nhep
 * <p>
 * This manages the Shared-preferences of the whole application, This will manage the userID
 * to keep a user logged in the entire application until they logout. Utilizes Shared-preferences
 * to store smaller data. Utilizes singleton method to only have one instance at a time, can be
 * called in any activity page. Contains a method to receive the userID, a getter function to get
 * the ID.
 */
public class AccountStatusCheck {

    private static final String PREFERENCE_FILE_NAME = "com.example.project2.PREF_FILE_NAME";
    private static final String ACCOUNT_USER_ID = "com.example.project2.ACCOUNT_USER_ID";
    private static final String CURRENT_ACTIVITY_KEY = "com.example.project2.CURRENT_ACTIVITY_KEY";

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static volatile AccountStatusCheck INSTANCE;

    private AccountStatusCheck(Application application){
        preferences = application.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    /**
     * singleton method to make sure theres only one instance of this
     */
    public static AccountStatusCheck getInstance(Application application){
        if (INSTANCE == null) {
            synchronized (AccountStatusCheck.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountStatusCheck(application);
                }
            }
        }
        return INSTANCE;
    }


    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        editor.putInt(ACCOUNT_USER_ID, userID);
        editor.apply();
    }

    public int getUserID(){
        return preferences.getInt(ACCOUNT_USER_ID, -1);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
