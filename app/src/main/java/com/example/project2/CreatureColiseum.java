package com.example.project2;

/**
 * This class will be called on start up
 * It will be used to instantiate global
 * Singletons that can be used throughout
 * The project as needed.
 * - Austin
 */

import android.app.Application;

import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;

import java.util.List;

public class CreatureColiseum extends Application {

    /**
     * This overrides the Apps onCreate method so that
     * anything that needs to be instantiated are guaranteed to
     * exist before they are called.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //checking to see if this is being called correctly
        android.util.Log.d("CreatureColiseum", "Application onCreate called");

        //Initializes shared preferences AccountStatusCheck(preferences manager)
        AccountStatusCheck.init(this);

        //Initializes ApplicationRepository to manipulate the user's information in database.
        ApplicationRepository.init(this);

        //initialize DAOs on app start up
        DAOProvider.init(this);

        //adds abilities to the database if not already there
        prepopulateAbilities();

        //initialize UserTeamData information on start up
        UserTeamData.getInstance();
    }

    private void prepopulateAbilities() {
        //this is run on a background thread so that changes to the DB can be made properly
        new Thread(() -> {
            //getting reference to the AbilityDAO that has been established earlier
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            /**
             * This checks to see if data for these pre established moves already exist in the
             * database. If they already exist, it means the DB has been initialized before and
             * therefore we do not need to add them again. If the app is freshly installed this
             * will run and populate the ability table with this information
             */
            if (abilityDAO.getAll().isEmpty()) {
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
