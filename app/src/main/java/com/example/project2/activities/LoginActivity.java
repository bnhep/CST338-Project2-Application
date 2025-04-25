package com.example.project2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.project2.UserTeamData;
import com.example.project2.creatures.Creature;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.AccountStatusCheck;
import com.example.project2.database.ApplicationRepository;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLoginBinding;
import com.example.project2.utilities.Converters;

import java.util.List;
import java.util.concurrent.Executors;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private ApplicationRepository repository;
    private AccountStatusCheck accountManager;
    boolean adminCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = ApplicationRepository.getInstance();
        accountManager = AccountStatusCheck.getInstance();

        if (accountManager.getUserID() != -1) {
            //user is already logged in
            String userId = String.valueOf(accountManager.getUserID());
            //load existing team by ID
            loadTeam(userId);
            //check if is admin
            Intent intent = accountManager.getIsAdminStatus() ?
                    AdminLandingActivity.AdminLandingIntentFactory(getApplicationContext()) :
                    UserLandingActivity.UserLandingPageIntentFactory(getApplicationContext());

            startActivity(intent);
            /**
             * Im putting these finish() calls because everywhere I read they say try to always
             * finish activities when youre done with them so that youre not
             * just adding more layers of activities onto the stack
             */
            finish();
            return;
        }

        /*
         * This button calls userValidation(); the method will validate username and password,
         * If it does not exist in the database it won't allow the user to sign in. User must go
         * create an account. If it does exist then validates user entry by checking if
         * username and password combination is correctly entered.
         */
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValidation();
            }
        });

        /*
         * This button calls signupActivity intent, swaps to sign up menu
         */
        binding.signUpButtonLoginMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignupActivity.signUpIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    /**
     * This function serves to validate the user's login information. Checks if user enters a
     * username, if not prompt user to enter one and not leave it blank. Create an observer to
     * manage the live data. If user enters a password and its not equal to the password in the
     * database then prompt invalid password else it will successfully login.
     */
    private void userValidation() {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditTextView.getText().toString();
        if (username.isEmpty() && password.isEmpty()){
            Toast.makeText(LoginActivity.this,
                    "Username and Password are blank.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username is blank. \nPlease enter a username",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    "Password is blank.\nPlease enter a password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (user != null) {
                    if (password.equals(user.getPassword())) {
                        //TODO: ADD A IF/ELSE TO DETERMINE IN USER IS AN ADMIN OR NOT
                        // IF THEY ARE AN ADMIN GO TO ADMIN SCREEN
                        Intent intent;
                        adminCheck = user.isAdmin();
                        if(!adminCheck) {
                            //moves to the MainActivity(basic user) page
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            loadTeam(String.valueOf(user.getId()));
                            intent = UserLandingActivity.UserLandingPageIntentFactory(getApplicationContext());
                        }
                        else{
                            //moves to the AdminLandingActivity page
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            loadTeam(String.valueOf(user.getId()));
                            intent = AdminLandingActivity.AdminLandingIntentFactory(getApplicationContext());
                        }
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Password Invalid. Please Enter a Password",
                                Toast.LENGTH_SHORT).show();
                        binding.passwordLoginEditTextView.setSelection(0);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, username + " does not exist.",
                            Toast.LENGTH_SHORT).show();
                    binding.usernameLoginEditText.setSelection(0);
                }
                // Remove the observer after the first change to avoid repeated checks
                userObserver.removeObserver(this);
            }
        });
    }

    public void loadTeam(String userId){
        //TODO: This is just here for testing. this should be moved to when the user logs in later
        try {
            //run on a background thread when making changes to the database
            Executors.newSingleThreadExecutor().execute(() -> {

                //clear any current data from the userTeam map
                UserTeamData.getInstance().clearTeam();

                //get reference to the CreatureDAO and AbilityDAO singletons
                CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
                AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

                //TODO:later on we want to pass in the actual users generated id here
                /**
                 * Passing the users ID into the creatureDAO to collect a list of
                 * creatures associated with the current user.
                 */
                List<CreatureEntity> creatureEntities = creatureDAO.getCreaturesByUserId(userId);

                //for each creature creature retrieved
                for (CreatureEntity entity : creatureEntities) {
                    /**
                     * pass that entities information into the converter to 'rehydrate'
                     * the creature object with the proper stats and abilities
                     */
                    Creature creature = Converters.convertEntityToCreature(entity, abilityDAO);
                    //store the saved team slot
                    int slot = entity.getTeamSlot();
                    //add the now rehydrated creature object into the userTeam map in the remembered slot
                    UserTeamData.getInstance().addCreatureToSlot(slot, creature);
                }
            });
        } catch (Exception e) {
            //im going to be sad if i see this
            Log.e("TeamBuilder", "Error loading team", e);
            runOnUiThread(() ->
                    Toast.makeText(LoginActivity.this, "Failed to load", Toast.LENGTH_SHORT).show()
            );
        }
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }


}