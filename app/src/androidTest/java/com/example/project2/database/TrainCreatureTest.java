package com.example.project2.database;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.project2.activities.TrainCreatureActivity;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.content.Context;

//Tests the train creature methods
@RunWith(AndroidJUnit4.class)
public class TrainCreatureTest {
    //sets up a train creature activity for testing
    private TrainCreatureActivity trainCreatureActivity;

    //Tests the written capitalization method
    @Test
    public void testCapitalize() {
        String result = trainCreatureActivity.capitalize("speed");
        assertEquals("Speed", result);
    }

    //tests the capitalization method
    @Test
    public void testCapitalize2(){
        String result = trainCreatureActivity.capitalize("health");
        assertEquals("Health", result);
    }

}
