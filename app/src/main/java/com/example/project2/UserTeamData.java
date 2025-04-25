package com.example.project2;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  user team singleton that keeps track of the users
 *  team data for easier access across the project whenever
 *  its needed. prevents multiple instances of the same team
 */

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

    /**
     * static singleton method
     * @return
     */
    public static synchronized UserTeamData getInstance() {
        if (instance == null) {
            instance = new UserTeamData();
        }
        return instance;
    }

    public Map<Integer, Creature> getUserTeam() {
        return userTeam;
    }

    /**
     * adds a creature to a specific spot on the hashmap
     * that stores the users team roster
     * @param slot
     * @param creature
     */
    public void addCreatureToSlot(int slot, Creature creature) {
        if (slot >= 1 && slot <=6) {
            userTeam.put(slot, creature);
        }
    }

    public Creature getCreatureAtSlot(int slot) {
        return userTeam.get(slot);
    }

    /**
     * never ended up using this method but when I created it I
     * figured it might be useful to swap around creatures positions
     * @param slotA
     * @param slotB
     */
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
