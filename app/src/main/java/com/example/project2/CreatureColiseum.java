package com.example.project2;

import android.app.Application;

import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;

import java.util.List;

public class CreatureColiseum extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.d("CreatureColiseum", "Application onCreate called");
        //initialize DAOs on app start up
        DAOProvider.init(this);

        //adds abilities to the database if not already there
        prepopulateAbilities();

        //initialize UserTeamData information on start up
        UserTeamData.getInstance();
    }

    private void prepopulateAbilities() {
        new Thread(() -> {
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            if (abilityDAO.getAll().isEmpty()) {  // Only add if the table is empty
                abilityDAO.insertAll(List.of(
                        new AbilityEntity("TACKLE", "tackle", "NORMAL", 25, 10, 100),
                        new AbilityEntity("FLAMETHROWER", "flamethrower", "FIRE", 25, 10, 100),
                        new AbilityEntity("RAZORLEAF", "razor leaf", "GRASS", 25, 10, 100),
                        new AbilityEntity("SHOCK", "shock", "ELECTRIC", 25, 10, 100),
                        new AbilityEntity("WATERJET", "water jet", "WATER", 25, 10, 100)
                ));
            }
        }).start();
    }
}
