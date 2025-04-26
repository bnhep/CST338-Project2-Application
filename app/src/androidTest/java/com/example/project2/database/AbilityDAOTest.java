package com.example.project2.database;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.entities.AbilityEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

@RunWith(AndroidJUnit4.class)
public class AbilityDAOTest {

    private ApplicationDatabase db;
    private AbilityDAO abilityDAO;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class)
                .allowMainThreadQueries()
                .build();
        abilityDAO = db.AbilityDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsertAndGetAbility() {
        AbilityEntity ability = new AbilityEntity(
                "THUNDER",      // abilityID
                "thunder",    // abilityName
                "ELECTRIC",       // abilityElement
                90,               // power
                10,               // critChance
                95                // accuracy
        );

        abilityDAO.insertAll(Collections.singletonList(ability));

        AbilityEntity retrieved = abilityDAO.getAbilityById("THUNDER");
        assertNotNull(retrieved);
        assertEquals(ability.getAbilityName(), retrieved.getAbilityName());
    }

    @Test
    public void testUpdateAbility() {
        AbilityEntity ability = new AbilityEntity(
                "TAILWHIP",      // abilityID
                "tail whip",    // abilityName
                "NORMAL",       // abilityElement
                20,               // power
                50,               // critChance
                100                // accuracy
        );

        abilityDAO.insertAll(Collections.singletonList(ability));

        ability.setPower(50);
        abilityDAO.update(ability);

        AbilityEntity updated = abilityDAO.getAbilityById("TAILWHIP");
        assertEquals(50, updated.getPower());
    }

    @Test
    public void testDeleteAbility() {
        AbilityEntity ability = new AbilityEntity(
                "FIREPUNCH",      // abilityID
                "fire punch",    // abilityName
                "FIRE",       // abilityElement
                65,               // power
                10,               // critChance
                100                // accuracy
        );

        abilityDAO.insertAll(Collections.singletonList(ability));

        abilityDAO.delete(ability);

        AbilityEntity deleted = abilityDAO.getAbilityById("FIREPUNCH");
        assertNull(deleted);
    }
}