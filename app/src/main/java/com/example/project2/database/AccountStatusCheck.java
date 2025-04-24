package com.example.project2.database;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author Brandon Nhep
 * <p>
 * This manages the Shared-preferences of the whole application, This will manage the userID
 * and username of the current logged in user. Utilizes Shared-preferences
 * to store smaller data. Utilizes singleton method to only have one instance at a time, can be
 * called in any activity page. Contains a method to receive the userID, a getter function to return
 * the ID. Contains a method to receive the username, a getter function to return the username.
 * Maintains values until a user logs out and by logging out will reset the preferences file.
 */
public class AccountStatusCheck {

    private static final String PREFERENCE_FILE_NAME = "com.example.project2.PREF_FILE_NAME";
    private static final String ACCOUNT_USER_ID = "com.example.project2.ACCOUNT_USER_ID";
    private static final String CURRENT_ACTIVITY_KEY = "com.example.project2.CURRENT_ACTIVITY_KEY";

    private static final String CURRENT_STATUS_KEY = "com.example.project2.CURRENT_STATUS_KEY ";
    private static final String CURRENT_USERNAME_KEY = "com.example.project2.CURRENT_USERNAME_KEY";

    private static final String TEAM_LOADED_KEY = "com.example.project2.TEAM_LOADED";

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static volatile AccountStatusCheck INSTANCE;


    private AccountStatusCheck(Context context){
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * singleton method to make sure theres only one instance of AccountStatusCheck class
     */
    public static void init(Context context){
        if (INSTANCE == null) {
            synchronized (AccountStatusCheck.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountStatusCheck(context);
                }
            }
        }
    }


    public static AccountStatusCheck getInstance(){
        return INSTANCE;
    }



    /**
     * Takes in a user ID and places it in the Shared preferences file under ACCOUNT_USER_ID key.
     * @param userID
     */
    public void setUserID(int userID) {
        editor.putInt(ACCOUNT_USER_ID, userID);
        editor.apply();
    }

    /**
     * Getter function to return the value tied with ACCOUNT_USER_ID, or in other words the current
     * logged in user's ID.
     * @return current logged in user's id
     */
    public int getUserID(){
        return preferences.getInt(ACCOUNT_USER_ID, -1);
    }

    /**
     * Clears the shared preferences file, called in the main activity log out button.
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }

    /**
     * Sets the current logged in user's username
     * @param username of the current logged in user.
     */
    public void setUserName(String username){
        editor.putString(CURRENT_USERNAME_KEY, username);
        editor.apply();
    }

    /**
     * Getter method to return the username of the current logged in user.
     * @return current logged in user's username
     */
    public String getUserName(){
        return preferences.getString(CURRENT_USERNAME_KEY, null);
    }

    public void setIsAdminStatus(boolean isAdmin) {
        editor.putBoolean(CURRENT_STATUS_KEY, isAdmin);
        editor.apply();
    }

    public boolean getIsAdminStatus(){
        return preferences.getBoolean(CURRENT_STATUS_KEY, true);
    }

    public boolean isTeamLoaded() {
        return preferences.getBoolean(TEAM_LOADED_KEY, false);
    }

    public void setTeamLoaded(boolean loaded) {
        editor.putBoolean(TEAM_LOADED_KEY, loaded);
        editor.apply();
    }

}
