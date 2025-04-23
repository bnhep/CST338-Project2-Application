package com.example.project2;

/**
 * This class will be called on start up
 * It will be used to instantiate global
 * Singletons that can be used throughout
 * The project as needed.
 * - Austin
 */

import android.app.Application;

import com.example.project2.creatures.ElectricRat;
import com.example.project2.creatures.FireLizard;
import com.example.project2.creatures.FlowerDino;
import com.example.project2.creatures.WeirdTurtle;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.utilities.Converters;

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

        //adds abilities and creatures to the database if not already there
        prepopulateCreaturesAndAbilities();

        //initialize UserTeamData information on start up
        UserTeamData.getInstance();
    }

    private void prepopulateCreaturesAndAbilities() {
        //this is run on a background thread so that changes to the DB can be made properly
        new Thread(() -> {
            //getting reference to the CreatureDAO that has been established earlier
            CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
            //getting reference to the AbilityDAO that has been established earlier
            AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

            /**
             * This checks to see if data for these pre established moves and creatures already
             * exist in the database. If they already exist, it means the DB has been initialized
             * before and therefore we do not need to add them again. If the app is freshly
             * installed these will run and populate the tables with needed information
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

            if (creatureDAO.getCreaturesByUserId("NONE").isEmpty()) {
                creatureDAO.insertAll(List.of(
                        Converters.convertCreatureToEntity(new ElectricRat(), "NONE", -1, 0),
                        Converters.convertCreatureToEntity(new FireLizard(), "NONE", -1, 0),
                        Converters.convertCreatureToEntity(new FlowerDino(), "NONE", -1, 0),
                        Converters.convertCreatureToEntity(new WeirdTurtle(), "NONE", -1, 0)
                ));
            }
        }).start();
    }
}
