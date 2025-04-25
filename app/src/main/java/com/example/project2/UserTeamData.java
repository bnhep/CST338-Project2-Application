package com.example.project2;

import com.example.project2.creatures.Creature;

import java.util.HashMap;
import java.util.Map;

public class UserTeamData {
    private static UserTeamData instance;
    //active team. key is slot, value is the creature held there
    private Map<Integer, Creature> userTeam;

    //only one instance of this should ever be present in the app so a private constructor is used
    private UserTeamData(){
        userTeam = new HashMap<>();
    }

    //static singleton method
    public static synchronized UserTeamData getInstance() {
        if (instance == null) {
            instance = new UserTeamData();
        }
        return instance;
    }

    public Map<Integer, Creature> getUserTeam() {
        return userTeam;
    }

    public void addCreatureToSlot(int slot, Creature creature) {
        if (slot >= 1 && slot <=6) {
            userTeam.put(slot, creature);
        }
    }

    public Creature getCreatureAtSlot(int slot) {
        return userTeam.get(slot);
    }

    public void swapCreatureToSlot(int slotA, int slotB) {
        Creature temp = userTeam.get(slotA);
        userTeam.put(slotA, userTeam.get(slotB));
        userTeam.put(slotB, temp);
    }

    public void removeCreatureFromSlot(int slot) {
        userTeam.remove(slot);
    }

    public void clearTeam(){
        userTeam.clear();
    }

    public int teamSize() {
        return userTeam.size();
    }

}
