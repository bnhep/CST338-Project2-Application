package com.example.project2;



import org.junit.Test;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import com.example.project2.database.entities.AbilityEntity;
import com.example.project2.utilities.Converters;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String abilityID;
    private String abilityName;
    private String abilityElement;
    private int power;
    private int critChance;
    private int accuracy;

    /**
     * @author Brandon Nhep
     */
    @Test
    public void testConvertEntityToAbility() {

        System.out.println("TESTING CONVERTING AN ENTITY TO ABILITY");
        AbilityEntity testEntity;
        testEntity = new AbilityEntity(abilityID, abilityName, abilityElement, power, critChance, accuracy);
        testEntity .setAbilityID("20");
        testEntity .setAbilityName("ELECTRONIC BLASTER");
        testEntity .setAbilityElement("ELECTRIC"); // assuming FIRE is a valid ElementalType
        testEntity .setPower(100);
        testEntity .setCritChance(15);
        testEntity .setAccuracy(60);
        System.out.println("Entity Ability ID: " + testEntity.getAbilityID());
        System.out.println("Entity Ability Name: " + testEntity.getAbilityName());
        System.out.println("Entity Ability Element: " + testEntity.getAbilityElement());
        System.out.println("Entity Ability Power: " + testEntity.getPower());
        System.out.println("Entity Ability Crit Chance: " + testEntity.getCritChance());
        System.out.println("Entity Ability Accuracy: " + testEntity.getAccuracy());

        //Tests if its instantiated correctly.
        assertNotNull(testEntity);
        assertEquals("20", testEntity.getAbilityID());
        assertEquals("ELECTRONIC BLASTER", testEntity.getAbilityName());
        assertEquals("ELECTRIC", testEntity.getAbilityElement());
        assertEquals(100, testEntity.getPower());
        assertEquals(15, testEntity.getCritChance());
        assertEquals(60, testEntity.getAccuracy());
        //calls the method
        Ability abilityFromEntity = Converters.convertEntityToAbility(testEntity);
        assertNotNull(abilityFromEntity);
        System.out.println("Successfully Converted");
        assertEquals("20", abilityFromEntity.getAbilityID());
        assertEquals("ELECTRONIC BLASTER", abilityFromEntity.getAbilityName());
        assertEquals(ElementalType.ELECTRIC, abilityFromEntity.getAbilityElement());
        assertEquals(100, abilityFromEntity.getPower());
        assertEquals(15, abilityFromEntity.getCritChance());
        assertEquals(60, abilityFromEntity.getAccuracy());
        assertEquals(testEntity.getAbilityID(), abilityFromEntity.getAbilityID());

        System.out.println("Ability object - Ability ID: " + testEntity.getAbilityID());
        System.out.println("Ability object -Ability Name: " + testEntity.getAbilityName());
        System.out.println("Ability object -Ability Element: " + testEntity.getAbilityElement());
        System.out.println("Ability object -Ability Power: " + testEntity.getPower());
        System.out.println("Ability object -Ability Crit Chance: " + testEntity.getCritChance());
        System.out.println("Ability object -Ability Accuracy: " + testEntity.getAccuracy());

    }


}