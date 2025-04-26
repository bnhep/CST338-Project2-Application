package com.example.project2;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.project2.activities.TrainCreatureActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TrainCreatureTest {
    private TrainCreatureActivity trainCreatureActivity;

    @Test
    public void testCapitalize() {
        String result = trainCreatureActivity.capitalize("speed");
        assertEquals("Speed", result);
    }

}
