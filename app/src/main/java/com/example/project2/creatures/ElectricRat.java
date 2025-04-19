package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: --/--/25
 * Explanation: Project 2: Creature Coliseum
 */

import android.util.Log;

import com.example.project2.ElementalType;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.utilities.Converters;

public class ElectricRat extends Creature {

    private final String PHRASE = "'lectra!";
    private final int HEALTH_MAX = 45;
    private final int ATTACK_MAX = 60;
    private final int DEFENSE_MAX = 35;
    private final int SPEED_MAX = 60;

    public ElectricRat() {

    }

    /**
     * This is the constructor for the creatures.ElectricRat class
     * @param name is what the monster object will be called
     */
    public ElectricRat(String name, int level) {
        super(name, level, ElementalType.ELECTRIC);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());

        //All electric rats start with shock
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        this.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("SHOCK")));
    }
}
