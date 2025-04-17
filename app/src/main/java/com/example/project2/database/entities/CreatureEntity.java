package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.ElementalType;
import com.example.project2.database.ApplicationDatabase;

import java.util.List;

@Entity(tableName = ApplicationDatabase.CREATURE_TABLE)
public class CreatureEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userId; //foreign key
    public String type; //subclass type
    public String name;
    public String phrase;
    public boolean fainted;

    public List<ElementalType> elements;
    public List<String> abilityList;

    public int level;
    public int curExperiencePoints;
    public int experienceNeededToLevel;
    public int curHealth;
    public int healthStat;
    public int baseHealth;
    public int attackStat;
    public int baseAttack;
    public int defenseStat;
    public int baseDefense;
    public int speedStat;
    public int baseSpeed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
