package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.ElementalType;

import java.util.List;

@Entity(tableName = "creatures")
public class CreatureEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userId; //foreign key
    public String type; //subclass type
    public String name;
    public String phrase;
    public boolean fainted;

    public List<ElementalType> elements;
    public List<String> abilityNames;

    public int level;
    public int curExperiencePoints;
    public int experienceNeededToLevel;
    public int curHealth;

    public int baseHealth;
    public int baseAttack;
    public int baseDefense;
    public int baseSpeed;
}
