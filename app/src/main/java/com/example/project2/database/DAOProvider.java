package com.example.project2.database;
/**
 * This will be used to handle DAO's as global singletons
 * because the individual DAO's will not be changing
 * throughout the project
 */


import android.app.Application;

public class DAOProvider {

    private static UserDAO userDAO;
    private static AbilityDAO abilityDAO;
    private static CreatureDAO creatureDAO;

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
