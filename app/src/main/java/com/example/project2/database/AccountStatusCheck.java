package com.example.project2.database;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author Brandon Nhep
 *
 * This manages the Sharedpreferences of the whole application, This will manage the userID
 * to keep a user logged in the entire application until they logout. Utilizes Sharedpreferences
 * to store smaller data. Utilizes singleton method to only have one instance at a time, can be
 * called in any activity page. Contains a method to receive the userID, a getter function to get
 * the ID.
 */
public class AccountStatusCheck {

    private static final String PREFERENCE_FILE_NAME = "com.example.project2.PREF_FILE_NAME";
    private static final String ACCOUNT_USER_ID = "com.example.project2.ACCOUNT_USER_ID";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static volatile AccountStatusCheck instance;

    private AccountStatusCheck(Application application){
        preferences = application.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    /** */
    public static AccountStatusCheck getInstance(Application application){
        if (instance == null) {
            synchronized (AccountStatusCheck.class) {
                if (instance == null) {
                    instance = new AccountStatusCheck(application);
                }
            }
        }
        return instance;
    }


    public void setUserID(int userID) {
        editor.putInt(ACCOUNT_USER_ID, userID);
        editor.apply();
    }

    public int getUserID(){
        return preferences.getInt(ACCOUNT_USER_ID, -1);
    }


}
