package com.example.project2.utilities;

import com.example.project2.creatures.*;
import com.example.project2.creatures.ElectricRat;
import com.example.project2.database.entities.CreatureEntity;

import java.util.ArrayList;

public class CreatureFactory {
    public static Creature fromEntity(CreatureEntity entity) {
        Creature creature = null;

        switch(entity.type) {
            case "ElectricRat":
                creature = new ElectricRat(entity.name, entity.level);
                break;
            case "FireLizard":
                creature = new FireLizard(entity.name, entity.level);
                break;
            case "FlowerDino":
                creature = new FlowerDino(entity.name, entity.level);
                break;
            case "WeirdTurtle":
                creature = new WeirdTurtle(entity.name, entity.level);
                break;
            default:
                throw new IllegalArgumentException("Unknown creature type: " + entity.type);
        }

        if (creature != null) {
            creature.setPhrase(entity.phrase);
            creature.setFainted(entity.fainted);
            creature.setCurExperiencePoints(entity.curExperiencePoints);
            creature.setExperienceNeededToLevel(entity.experienceNeededToLevel);
            creature.setCurHealth(entity.curHealth);
            creature.setBaseStats(entity.baseHealth, entity.baseAttack, entity.baseDefense, entity.baseSpeed);
            creature.getElements().clear();
            creature.getElements().addAll(entity.elements);
        }

        return creature;
    }

    public static CreatureEntity toEntity(Creature creature, String userId) {
        CreatureEntity entity = new CreatureEntity();

        entity.userId = userId;
        entity.type = creature.getClass().getSimpleName();
        entity.name = creature.getName();
        entity.phrase = creature.getPhrase();
        entity.fainted = creature.isFainted();

        entity.level = creature.getLevel();
        entity.curExperiencePoints = creature.getCurExperiencePoints();
        entity.experienceNeededToLevel = creature.getExperienceNeededToLevel();
        entity.curHealth = creature.getCurHealth();

        entity.baseHealth = creature.getBaseHealth();
        entity.baseAttack = creature.getBaseAttack();
        entity.baseDefense = creature.getBaseDefense();
        entity.baseSpeed = creature.getBaseSpeed();

        entity.elements = new ArrayList<>(creature.getElements());

        return entity;
    }
}
