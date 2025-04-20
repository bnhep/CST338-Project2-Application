package com.example.project2.database;
/**
 * This will be used to handle DAO's as global singletons
 * because the individual DAO's will not be changing
 * throughout the project. This helps cut down on rewriting
 * code and keeps things looking cleaner
 */


import android.app.Application;

public class DAOProvider {

    //private variable for the UserDAO as a global singleton
    private static UserDAO userDAO;

    //private variable for the AbilityDAO as a global singleton
    private static AbilityDAO abilityDAO;

    //private variable for the CreatureDAO as a global singleton
    private static CreatureDAO creatureDAO;

    /**
     * This method will be called by the CreatureColiseum class
     * on start up so that these global instances are guaranteed
     * to be available when needed.
     * @param applicationContext
     */
    public static void init(Application applicationContext) {
        ApplicationDatabase db = ApplicationDatabase.getDatabase(applicationContext);
        userDAO = db.UserDAO();
        abilityDAO = db.AbilityDAO();
        creatureDAO = db.CreatureDAO();
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static AbilityDAO getAbilityDAO() {
        return abilityDAO;
    }

    public static CreatureDAO getCreatureDAO() {
        return creatureDAO;
    }
}
