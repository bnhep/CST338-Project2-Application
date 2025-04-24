package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.ElementalType;
import com.example.project2.database.ApplicationDatabase;

import java.util.List;

@Entity(tableName = ApplicationDatabase.CREATURE_TABLE)
public class CreatureEntity {
    @PrimaryKey(autoGenerate = true)
    private int creatureId;

    private String userId; //foreign key
    private String type; //subclass type
    private String name;
    private String phrase;
    private boolean fainted;

    private List<ElementalType> elements;
    private List<String> abilityList;

    private int level;
    private int curExperiencePoints;
    private int experienceNeededToLevel;
    private int curHealth;
    private int healthStat;
    private int baseHealth;
    private int attackStat;
    private int baseAttack;
    private int defenseStat;
    private int baseDefense;
    private int speedStat;
    private int baseSpeed;
    private int bonusPointTotal;
    private int bonusHealth;
    private int bonusAttack;
    private int bonusDefense;
    private int bonusSpeed;
    private int teamSlot;

    public CreatureEntity(int creatureId, String userId, String type, String name, String phrase, boolean fainted, List<ElementalType> elements, List<String> abilityList, int level, int curExperiencePoints, int experienceNeededToLevel, int curHealth, int healthStat, int baseHealth, int attackStat, int baseAttack, int defenseStat, int baseDefense, int speedStat, int baseSpeed, int bonusPointTotal, int bonusHealth, int bonusAttack, int bonusDefense, int bonusSpeed, int teamSlot) {
        this.creatureId = creatureId;
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.phrase = phrase;
        this.fainted = fainted;
        this.elements = elements;
        this.abilityList = abilityList;
        this.level = level;
        this.curExperiencePoints = curExperiencePoints;
        this.experienceNeededToLevel = experienceNeededToLevel;
        this.curHealth = curHealth;
        this.healthStat = healthStat;
        this.baseHealth = baseHealth;
        this.attackStat = attackStat;
        this.baseAttack = baseAttack;
        this.defenseStat = defenseStat;
        this.baseDefense = baseDefense;
        this.speedStat = speedStat;
        this.baseSpeed = baseSpeed;
        this.bonusPointTotal = bonusPointTotal;
        this.bonusHealth = bonusHealth;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.bonusSpeed = bonusSpeed;
        this.teamSlot = teamSlot;
    }

    public int getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(int creatureId) {
        this.creatureId = creatureId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(List<String> abilityList) {
        this.abilityList = abilityList;
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

    public int getBonusPointTotal() {
        return bonusPointTotal;
    }

    public void setBonusPointTotal(int bonusPointTotal) {
        this.bonusPointTotal = bonusPointTotal;
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

    public int getTeamSlot() {
        return teamSlot;
    }

    public void setTeamSlot(int teamSlot) {
        this.teamSlot = teamSlot;
    }
}
