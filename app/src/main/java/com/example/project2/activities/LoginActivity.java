package com.example.project2.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

/**
 * This class will handle the login activity for the user. The user will be prompted to enter
 * a username and password to enter the application's landing screens. Offer the user ability to
 * change their passwords if they forgot it or if they do not have an account then sign up. The
 * user will only be able to login if they have an account created and that account is stored in
 * the database under userTable. Validates for blanks, existence of the username, wrong password
 * or wrong username/password combination. If a user successfully logs in then their unique id,
 * isAdmin boolean, and username will be stored in a shared preference file to keep track of their
 * login status and the creature lists associated with the user's unique id will be loaded to the
 * game. If the user never logs out and force quits the app they will be validated on whether
 * their id exists in the shared preference file. If it does then it will auto switch to landing
 * page essentially keeping the user logged in. Else it will keep them on the log in page.
 * @author Brandon Nhep
 * Date: 4/21/25
 */
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private ApplicationRepository repository;
    private AccountStatusCheck accountManager;
    boolean adminCheck = false;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(binding.getRoot());

        repository = ApplicationRepository.getInstance();
        accountManager = AccountStatusCheck.getInstance();

        /*
         * Checks if the user is already logged in or essentially their id is still in the
         * shared preference file. The only way it is not is when they logout in the landing screen
         * due to logging out deleting the contents of the shared preference file.
         */
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
             * - Austin
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

        /*
         * Forgot password button leading to ForgotPasswordActivity
         */
        binding.forgotPassWordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForgotPasswordActivity
                        .forgotPasswordIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    /**
     * This function serves to validate the user's login information. Checks if user enters a
     * username, if not prompt user to enter one and not leave it blank. Create an observer to
     * manage the live data. If user enters a password and its not equal to the password in the
     * database then prompt invalid password else it will successfully login. On successful login
     * of basic user or admin user, the associated unique ID, username, isAdmin boolean will be
     * stored in a shared preference file. In addition it will load the creature data associated
     * with the unique id into an array list for the game.
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
            Toast.makeText(LoginActivity.this,
                    "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    "Please enter a password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (password.equals(user.getPassword())) {
                        Intent intent;
                        adminCheck = user.isAdmin();
                        if(!adminCheck) {
                            //moves to the UserLandingActivity page
                            //Stores stuff into shared preference and load creature data associated
                            //with the account
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            loadTeam(String.valueOf(user.getId()));
                            intent = UserLandingActivity
                                    .UserLandingPageIntentFactory(getApplicationContext());
                        }
                        else{
                            //moves to the AdminLandingActivity page
                            //Stores stuff into shared preference and load creature data associated
                            //with the account
                            accountManager.setUserID(user.getId());
                            accountManager.setUserName(user.getUsername());
                            accountManager.setIsAdminStatus(user.isAdmin());
                            loadTeam(String.valueOf(user.getId()));
                            intent = AdminLandingActivity
                                    .AdminLandingIntentFactory(getApplicationContext());
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Password is incorrect.", Toast.LENGTH_SHORT).show();
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

    /**
     * used to load creatures from the database by user ID
     * and populate the users team with found information
     * - Austin
     * @param userId
     */
    public void loadTeam(String userId){
        try {
            //run on a background thread when making changes to the database
            Executors.newSingleThreadExecutor().execute(() -> {

                //clear any current data from the userTeam map
                UserTeamData.getInstance().clearTeam();

                //get reference to the CreatureDAO and AbilityDAO singletons
                CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
                AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

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
                    Toast.makeText(LoginActivity.this,
                            "Failed to load", Toast.LENGTH_SHORT).show()
            );
        }
    }

    /**
     * This is the intent factory method for this activity. If the method is called and the return
     * value is stored in an intent. This will be passed to a start activity method in order to
     * swap to this activity.
     * @param context
     * @return new instantiated intent
     */
    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }


}