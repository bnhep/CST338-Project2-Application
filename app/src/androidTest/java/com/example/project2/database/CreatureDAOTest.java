package com.example.project2.database;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.ElementalType;
import com.example.project2.database.entities.CreatureEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CreatureDAOTest {

    private ApplicationDatabase db;
    private CreatureDAO creatureDAO;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).allowMainThreadQueries().build();
        creatureDAO = db.CreatureDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsertAndGetCreature() {
        CreatureEntity creature = new CreatureEntity(
                1,             //creatureId
                "NONE",                 //userId
                "ElectricRat",          //type
                "Zaps",                 //name
                "Chu",                  //phrase
                false,                  //fainted
                new ArrayList<>(),      //elements
                new ArrayList<>(),      //abilityList
                20,                     //level
                0,                      //curExperiencePoints
                100,                    //experienceNeededToLevel
                35,                     //curHealth
                35,                     //healthStat
                35,                     //baseHealth
                55,                     //attackStat
                55,                     //baseAttack
                40,                     //defenseStat
                40,                     //baseDefense
                90,                     //speedStat
                90,                     //baseSpeed
                0,                      //bonusStatTotal
                0,                      //bonusHealth
                0,                      //bonusAttack
                0,                      //bonusDefense
                0,                      //bonusSpeed
                1                       //teamSlot
        );

        creatureDAO.insert(creature);

        CreatureEntity retrieved = creatureDAO.getCreatureById(1);
        assertNotNull(retrieved);
        assertEquals(creature.getName(), retrieved.getName());
    }

    @Test
    public void testUpdateCreature() {
        CreatureEntity creature = new CreatureEntity(
                2,             //creatureId
                "NONE",                 //userId
                "ElectricRat",          //type
                "Zaps",                 //name
                "Chu",                  //phrase
                false,                  //fainted
                new ArrayList<>(),      //elements
                new ArrayList<>(),      //abilityList
                20,                     //level
                0,                      //curExperiencePoints
                100,                    //experienceNeededToLevel
                35,                     //curHealth
                35,                     //healthStat
                35,                     //baseHealth
                55,                     //attackStat
                55,                     //baseAttack
                40,                     //defenseStat
                40,                     //baseDefense
                90,                     //speedStat
                90,                     //baseSpeed
                0,                      //bonusStatTotal
                0,                      //bonusHealth
                0,                      //bonusAttack
                0,                      //bonusDefense
                0,                      //bonusSpeed
                1                       //teamSlot
        );

        creatureDAO.insert(creature);

        creature.setCurHealth(80);
        creatureDAO.update(creature);

        CreatureEntity updated = creatureDAO.getCreatureById(2);
        assertEquals(80, updated.getCurHealth());
    }

    @Test
    public void testDeleteCreature() {
        CreatureEntity creature = new CreatureEntity(
                3,             //creatureId
                "NONE",                 //userId
                "ElectricRat",          //type
                "Zaps",                 //name
                "Chu",                  //phrase
                false,                  //fainted
                new ArrayList<>(),      //elements
                new ArrayList<>(),      //abilityList
                20,                     //level
                0,                      //curExperiencePoints
                100,                    //experienceNeededToLevel
                35,                     //curHealth
                35,                     //healthStat
                35,                     //baseHealth
                55,                     //attackStat
                55,                     //baseAttack
                40,                     //defenseStat
                40,                     //baseDefense
                90,                     //speedStat
                90,                     //baseSpeed
                0,                      //bonusStatTotal
                0,                      //bonusHealth
                0,                      //bonusAttack
                0,                      //bonusDefense
                0,                      //bonusSpeed
                1                       //teamSlot
        );

        creatureDAO.insert(creature);

        creatureDAO.delete(creature);

        CreatureEntity deleted = creatureDAO.getCreatureById(3);
        assertNull(deleted);
    }
}