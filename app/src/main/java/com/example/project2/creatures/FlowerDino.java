package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  updated flower dino Creature subclass
 */

import com.example.project2.ElementalType;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.utilities.Converters;

public class FlowerDino extends Creature {

    private final String PHRASE = "'Boaba!";
    private final int HEALTH_MAX = 90;
    private final int ATTACK_MAX = 40;
    private final int DEFENSE_MAX = 45;
    private final int SPEED_MAX = 25;

    /**
     * default constructor used with establishing this creatures template
     */
    public FlowerDino() {
        super("Flower Dino", 1, ElementalType.GRASS);
        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());

        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        if (abilityDAO != null) {
            this.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("RAZORLEAF")));
        }
    }

    /**
     * constructor used when building a creature from an entity
     * @param name
     * @param level
     */
    public FlowerDino(String name, int level) {
        super(name, level, ElementalType.GRASS);

        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());

        //All flower dinos start with razor leaf
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        this.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("RAZORLEAF")));
    }

    //TODO: these are unneeded and should probably be removed
    public int getHEALTH_MAX(){
        return HEALTH_MAX;
    }
    public int getATTACK_MAX(){
        return ATTACK_MAX;
    }
    public int getDEFENSE_MAX(){
        return DEFENSE_MAX;
    }
    public int getSPEED_MAX(){
        return SPEED_MAX;
    }
}
