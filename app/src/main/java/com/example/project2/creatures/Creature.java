package com.example.project2.creatures;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  An updated and altered Creature class that now not
 *  only stores more information about the creature that
 *  is needed for the game, but also contains updated
 *  methods that are used for a more complex battle system
 */

import com.example.project2.Ability;
import com.example.project2.ElementalType;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.utilities.Converters;
import com.example.project2.utilities.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Creature {
    //general creature info
    private int creatureId;
    private String type;
    private String name = "";
    private String phrase = "";
    private boolean fainted = false;

    private List<ElementalType> elements = new ArrayList<ElementalType>();
    private List<Ability> abilityList = new ArrayList<Ability>();

    private int level = 1;
    private final int LEVEL_MAX = 20;

    private int curExperiencePoints = 0;
    private int experienceNeededToLevel = 10;

    //stats
    private int curHealth;
    private int healthStat;
    private int baseHealth;
    private int attackStat;
    private int baseAttack;
    private int defenseStat;
    private int baseDefense;
    private int speedStat;
    private int baseSpeed;
    private int bonusStatTotal;
    private int bonusHealth;
    private int bonusAttack;
    private int bonusDefense;
    private int bonusSpeed;

    /**
     * default constructor that is used when added a template creature into the database
     */
    public Creature() {
        this.level = 1;
        this.experienceNeededToLevel = calculateExperienceNeeded(level);

        //All creatures start with tackle
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        this.abilityList.add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("TACKLE")));
    }

    /**
     * constructor that is used when when creating a creature from a subclass
     * @param name
     * @param level
     * @param types
     */
    public Creature(String name, int level, ElementalType... types) {
        this.name = name;
        this.level = level;
        this.elements.addAll(Arrays.asList(types));
        this.experienceNeededToLevel = calculateExperienceNeeded(level);

        //All creatures start with tackle
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();
        this.abilityList.add(Converters.convertEntityToAbility(abilityDAO.getAbilityById("TACKLE")));
    }

    public int getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(int creatureId) {
        this.creatureId = creatureId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    public List<ElementalType> getElements() {
        return elements;
    }

    public void setElements(List<ElementalType> elements) {
        this.elements = elements;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurExperiencePoints() {
        return curExperiencePoints;
    }

    public void setCurExperiencePoints(int curExperiencePoints) {
        this.curExperiencePoints = curExperiencePoints;
    }

    public int getExperienceNeededToLevel() {
        return experienceNeededToLevel;
    }

    public void setExperienceNeededToLevel(int experienceNeededToLevel) {
        this.experienceNeededToLevel = experienceNeededToLevel;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public int getHealthStat() {
        return healthStat;
    }

    public void setHealthStat(int healthStat) {
        this.healthStat = healthStat;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getAttackStat() {
        return attackStat;
    }

    public void setAttackStat(int attackStat) {
        this.attackStat = attackStat;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getDefenseStat() {
        return defenseStat;
    }

    public void setDefenseStat(int defenseStat) {
        this.defenseStat = defenseStat;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getSpeedStat() {
        return speedStat;
    }

    public void setSpeedStat(int speedStat) {
        this.speedStat = speedStat;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBonusStatTotal() {
        return bonusStatTotal;
    }

    public void setBonusStatTotal(int bonusStatTotal) {
        this.bonusStatTotal = bonusStatTotal;
    }

    public int getBonusHealth() {
        return bonusHealth;
    }

    public void setBonusHealth(int bonusHealth) {
        this.bonusHealth = bonusHealth;
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }

    public int getBonusSpeed() {
        return bonusSpeed;
    }

    public void setBonusSpeed(int bonusSpeed) {
        this.bonusSpeed = bonusSpeed;
    }

    public void setBaseStats(int baseHealth, int baseAttack, int baseDefense, int baseSpeed) {
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
    }

    public int calculateStat(int baseStat, int bonusStat) {
        return Math.round(baseStat * ((float) level / LEVEL_MAX));
    }

    /**
     * used to calculate a creatures stats that will be used during battle
     */
    public void updateStats() {
        healthStat = (calculateStat(baseHealth, bonusHealth)*2)+this.getLevel();
        attackStat = calculateStat(baseAttack, bonusAttack);
        defenseStat = calculateStat(baseDefense, bonusDefense);
        speedStat = calculateStat(baseSpeed, bonusSpeed);
    }

    /**
     * used to gain experience after a battle and determine if a creature
     * has leveled up as a result of gaining XP
     * @param experience
     */
    public void gainExperience(int experience) {
        if (level == LEVEL_MAX) {
            curExperiencePoints = 0;
            return;
        }

        curExperiencePoints += experience;
        if (curExperiencePoints >= experienceNeededToLevel) {
            curExperiencePoints = curExperiencePoints-experienceNeededToLevel;
            levelUp();
        }
    }

    /**
     * used to determine the amount of XP need for a creature
     * to level up. As they level up, more XP should be needed to level
     * @param level
     * @return
     */
    public int calculateExperienceNeeded(int level) {
        int baseXP = 10;
        double growthRate = 1.4;
        return (int) Math.round(baseXP * Math.pow(growthRate, level - 1));
    }

    /**
     * used to level a creature up after reaching an XP threshold
     * this will also call for a creatures stats to be recalculated
     * as they should be increased as they level up
     */
    public void levelUp() {
        level++;
        updateStats();
        curHealth = healthStat;
        experienceNeededToLevel = calculateExperienceNeeded(this.getLevel());
    }

    /**
     * New fun method made to determine the results of an attack made. It accepts
     * a target and an ability and determines if the attack missed, critically hit,
     * and also passes along the amount of damage that has been calculated as a
     * result of the takeDamage method. This could be removed from creature and instead
     * placed into a new class for battle logic along with other combat related methods
     * but I decided to keep it here as a nod to the fact that this is an upgrade of
     * our previous Legally Distinct Pocket Monsters project
     * @param target
     * @param ability
     * @return
     */
    public double[] attack(Creature target, Ability ability) {
        //percentage rolls
        int accuracyRoll = Dice.roll(100);
        int critChanceRoll = Dice.roll(100);

        double critFlag = 1.0;

        if (accuracyRoll > ability.getAccuracy()) {
            //attack missed
            //no damage, no elemental modifier, flag as a missed
            return new double[] {0.0, 1.0, 0.0};
        }

        //calculate damage and store result
        double[] result = calculateDamage(target, ability);
        double attackValue = result[0];
        double elementalModifier = result[1];

        if (!(critChanceRoll > ability.getCritChance())) {
            //critical hit
            critFlag = 2.0; //set flag to show crit
            attackValue *= 2; //double damage
        }

        //pass the damage result onto the deal damage to the target
        target.takeDamage(attackValue);

        //return damage, elemental modifier, flag as a hit or crit
        return new double[] {attackValue, elementalModifier, critFlag};
    }

    /**
     * this is used to determine the damage resulting from the ability
     * attacking the target. In this class the abilities power is used along
     * side the attacking creatures attack, and the defending creatures defense
     * to calculate an amount of damage. this result is then multiplied by an
     * elemental modifier and a Same Type Attack Bonus (STAB checks if a creature uses
     * an ability with a matching element as themselves) to determine the final result.
     * An array is returned as this way I can pass both the damage, and the
     * elemental modifier so that the attack can be flagged as super effective
     * ot not very effective based on the result of the elementalDamageModifier method
     * @param target
     * @param ability
     * @return
     */
    public double[] calculateDamage(Creature target, Ability ability) {
        double damageTotal;
        double elementalModifier = 1.0;
        double STABModifier = 1.0;

        if (this.elements.contains(ability.getAbilityElement())) {
            STABModifier = 1.5;
        }

        for (int i = 0; i < target.elements.size(); i++) {
            elementalModifier *= elementalDamageModifier(target.elements.get(i), ability.getAbilityElement());
        }

        //damage formula
        double attackDefenseRatio = Math.pow((double) this.getAttackStat() / (this.getAttackStat() + target.getDefenseStat()), .85);
        double baseDamage = (this.getLevel() / 2.0) * ability.getPower();
        double modifierBonus = Math.pow(elementalModifier * STABModifier, .85);
        damageTotal = baseDamage * attackDefenseRatio * modifierBonus / 4;

        //return a double that stores both the damage and modifier
        return new double[] {damageTotal, elementalModifier};
    }

    /**
     * this has been mostly untouched... mostly. Uses the targeted creatures
     * elements list and compares it to the attacking type of the ability used
     * to determine if the move is super effective or not very effective
     * @param defending
     * @param abilityElement
     * @return
     */
    double elementalDamageModifier(ElementalType defending, ElementalType abilityElement) {
        //switch statement that checks the value of the passed in element
        switch (defending) {
            //depending on the passed in element checks are given different results
            case ELECTRIC:
                //if defending element is electric and the attack is electric
                if (abilityElement == ElementalType.ELECTRIC) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case FIRE:
                //if defending element is fire and the attack is water
                if (abilityElement == ElementalType.WATER) {
                    return 2.0;
                }
                //if defending element is fire and the attack is fire or grass
                if (abilityElement == ElementalType.FIRE
                || abilityElement == ElementalType.GRASS) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case GRASS:
                //if the defending element it grass and the attack is fire
                if (abilityElement == ElementalType.FIRE) {
                    return 2.0;
                }
                //if the defending element is grass and the attack is electric, grass or water
                if (abilityElement == ElementalType.ELECTRIC
                || abilityElement == ElementalType.GRASS
                || abilityElement == ElementalType.WATER) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            case WATER:
                //if the defending element it water and the attack is electric
                if (abilityElement == ElementalType.ELECTRIC
                    || abilityElement == ElementalType.GRASS) {
                return 2.0;
                }
                //if the defending element it water and the attack is fire or water
                if (abilityElement == ElementalType.FIRE
                || abilityElement == ElementalType.WATER) {
                    return 0.5;
                }
                //No interaction
                return 1.0;

            default:
                //No currently listed element
                return 1.0;
        }
    }

    /**
     * used to reduce the current amount of health a creature
     * has by the damage amount passed in
     * @param attackValue
     */
    public void takeDamage(double attackValue) {
        int damage = (int) Math.round(attackValue);

        //make sure the attack dealt damage
        if (damage > 0) {
            this.curHealth = this.curHealth - damage;
        }

        if (this.getCurHealth() <= 0) {
            this.curHealth = 0;
            this.setFainted(true);
        }
    }
}
