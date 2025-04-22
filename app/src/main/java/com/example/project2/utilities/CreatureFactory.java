package com.example.project2.utilities;

import com.example.project2.creatures.Creature;
import com.example.project2.database.AbilityDAO;
import com.example.project2.database.CreatureDAO;
import com.example.project2.database.DAOProvider;
import com.example.project2.database.entities.CreatureEntity;

public class CreatureFactory {
    public static Creature createCreatureFromTemplate(String type, String userId) {
        CreatureDAO creatureDAO = DAOProvider.getCreatureDAO();
        AbilityDAO abilityDAO = DAOProvider.getAbilityDAO();

        CreatureEntity template = creatureDAO.getCreatureTemplateByType(type);
        Creature newCreature = Converters.convertEntityToCreature(template, abilityDAO);

        //assign new name, ID, etc.
        newCreature.setName(template.getName());
        newCreature.setCreatureId(0); //room will generate new ID

        return newCreature;
    }
}
