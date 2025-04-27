package com.example.project2.database;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Before;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import com.example.project2.R;
import com.example.project2.activities.AddMonstersActivity;

public class AddMonstersActivityTest {

    @Before
    public void setUp() {
        // Set up the environment for the test
        ActivityScenario.launch(AddMonstersActivity.class);
    }

    @Test
    public void testButtonVisibility() {
        // Test if the buttons are displayed correctly
        onView(withId(R.id.backButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.createMonsterButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.grassButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.fireButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.waterButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.normalButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testTitleDisplay() {
        // Test if the Title "Welcome to Monster Creation" is displayed
        onView(withId(R.id.Title)).check(ViewAssertions.matches(withText("Welcome to Monster Creation")));
    }

    @Test
    public void testVisibilityOfTypeButtons() {
        // Test if monster type buttons are visible
        onView(withId(R.id.grassButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.fireButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.waterButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.normalButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}