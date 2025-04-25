package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  updated electric rat Creature subclass
 */

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

    /**
     * default constructor used with establishing this creatures template
     */
    public ElectricRat() {
        super("Electric Rat", 1, ElementalType.ELECTRIC);
        setType(this.getClass().getSimpleName());
        setPhrase(PHRASE);
        setBaseStats(HEALTH_MAX, ATTACK_MAX, DEFENSE_MAX, SPEED_MAX);
        updateStats();
        setCurHealth(this.getHealthStat());

        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        if (abilityDAO != null) {
            this.getAbilityList().add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("SHOCK")));
        }
    }

    /**
     * constructor used when building a creature from an entity
     * @param name
     * @param level
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
