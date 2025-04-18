package com.example.project2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.database.ApplicationDatabase;

@Entity(tableName = ApplicationDatabase.ABILITY_TABLE)
public class AbilityEntity {

    @PrimaryKey
    @NonNull
    private String abilityID;

    private String abilityName;
    private String abilityElement;
    private int power;
    private int critChance;
    private int accuracy;

    public AbilityEntity(@NonNull String abilityID, String abilityName, String abilityElement, int power, int critChance, int accuracy) {
        this.abilityID = abilityID;
        this.abilityName = abilityName;
        this.abilityElement = abilityElement;
        this.power = power;
        this.critChance = critChance;
        this.accuracy = accuracy;
    }

    @NonNull
    public String getAbilityID() {
        return abilityID;
    }

    public void setAbilityID(@NonNull String abilityID) {
        this.abilityID = abilityID;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getAbilityElement() {
        return abilityElement;
    }

    public void setAbilityElement(String abilityElement) {
        this.abilityElement = abilityElement;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
