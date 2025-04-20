package com.example.project2;
/**
 * *********************** DEPRECATED ***********************
 * THIS CLASS IS ONLY BEING USED FOR REFERENCE
 * AND WILL BE REMOVED ONCE NO LONGER NEEDED
 * *********************** DEPRECATED ***********************
 */

import com.example.project2.creatures.*;

import java.util.ArrayList;
import java.util.Scanner;

public class PocketCreaturesArena {
    Scanner scan = new Scanner(System.in);

    private final ArrayList<Creature> pocketCreatureTeam = new ArrayList<>();
    Ability tackle = new Ability("001", "Tackle", ElementalType.NORMAL, 20, 10, 100);
    Ability flamethrower = new Ability("002", "flamethrower", ElementalType.FIRE, 30, 10, 100);
    Ability razorLeaf = new Ability("003", "razor leaf", ElementalType.GRASS, 25, 20, 90);
    Ability shock = new Ability("004", "shock", ElementalType.ELECTRIC, 40, 10, 90);
    Ability waterJet = new Ability("005", "water Jet", ElementalType.WATER, 25, 10, 100);

    public PocketCreaturesArena() {

    }

    public void play() {
//        boolean quitGame = false;
//        System.out.println("Welcome to Pocket Creatures Arena!");
//
//        while (!quitGame) {
//            System.out.println("""
//                    ---------------
//                    Select from the following:
//                    1: Battle
//                    2: Edit team
//                    3: Train team
//                    4: Save/Load team
//                    0: to exit
//                    ---------------"""
//            );
//
//            System.out.print("Selection: ");
//            String selection = scan.nextLine().trim().toLowerCase();
//
//            switch (selection) {
//                case "1":
//                    System.out.println("You selected Battle");
//                    chooseBattle();
//                    break;
//                case "2":
//                    System.out.println("You selected Edit team");
//                    editTeam();
//                    break;
//                case "3":
//                    System.out.println("You selected Train Team");
//                    trainTeam();
//                    break;
//                case "4":
//                    System.out.println("You selected Save/Load team");
//                    saveLoadTeam();
//                    break;
//                case "0":
//                    quitGame = true;
//                    break;
//                default:
//                    System.out.println("No \"" + selection + "\" option. Please select from the following." );
//                    break;
//            }
//        }
//
//        System.out.println("Until next time!");
    }

    public void chooseBattle() {
//        boolean exitMenu = false;
//        System.out.println("Pocket creatures arena battle!");
//
//        while(!exitMenu) {
//            displayTeam("Who will you battle with?");
//
//            System.out.print("Selection: ");
//            int selection = Integer.parseInt(scan.nextLine().trim().toLowerCase());
//
//            if (selection == 0) {
//                return;
//            }
//
//            WeirdTurtle tide = new WeirdTurtle("Tide", 5);
//            tide.getAbilityList().add(tackle);
//            tide.getAbilityList().add(waterJet);
//            tide.getAbilityList().add(razorLeaf);
//
//            battle(pocketCreatureTeam.get(selection-1), tide);
//        }
    }

    public void battle(Creature playerCreature, Creature opponentCreature) {
//        boolean exitMenu = false;
//
//        while (!exitMenu) {
//            System.out.println(opponentCreature.getName() + "'s HP: " + opponentCreature.getCurHealth());
//            System.out.println(playerCreature.getName() + "'s HP: " + playerCreature.getCurHealth());
//            displayBattleOptions(playerCreature);
//
//            System.out.print("Selection: ");
//            int selection = Integer.parseInt(scan.nextLine().trim().toLowerCase());
//
//            if (playerCreature.getSpeedStat() >= opponentCreature.getSpeedStat()) {
//                playerCreature.attack(opponentCreature, playerCreature.getAbilityList().get(selection-1));
//                if (opponentCreature.isFainted()) {
//                    System.out.println(opponentCreature.getName() + " has fainted. You win!");
//                    playerCreature.gainExperience((int) Math.round(opponentCreature.getLevel() * 1.4));
//                    return;
//                }
//                opponentCreature.attack(playerCreature, opponentCreature.getAbilityList().get(Dice.roll(0, (opponentCreature.getAbilityList().size()-1))));
//                if (playerCreature.isFainted()) {
//                    System.out.println(playerCreature.getName() + " has fainted. You lose.");
//                    return;
//                }
//            }
//            else {
//                opponentCreature.attack(playerCreature, opponentCreature.getAbilityList().get(Dice.roll(0, (opponentCreature.getAbilityList().size()-1))));
//                if (playerCreature.isFainted()) {
//                    System.out.println(playerCreature.getName() + " has fainted. You lose.");
//                    return;
//                }
//                playerCreature.attack(opponentCreature, playerCreature.getAbilityList().get(selection-1));
//                if (opponentCreature.isFainted()) {
//                    System.out.println(opponentCreature.getName() + " has fainted. You win!");
//                    playerCreature.gainExperience((int) Math.round(opponentCreature.getLevel() * 1.4));
//                    return;
//                }
//            }
//        }
    }

    public void editTeam() {
//        boolean exitMenu = false;
//        System.out.println("Build your team!");
//
//        while(!exitMenu) {
//            displayTeam("Select a team member to edit:");
//            System.out.print("Selection: ");
//            String selection = scan.nextLine().trim().toLowerCase();
//
//            if ((pocketCreatureTeam.size() < Integer.parseInt(selection)) && (Integer.parseInt(selection) < 7)) {
//                System.out.println("Team slot is empty." );
//                System.out.println("Creating new pocket creature." );
//                createPocketCreature();
//                continue;
//            }
//
//            switch (selection) {
//                case "1": case "2": case "3": case "4": case "5": case "6":
//                    System.out.println("You selected " + pocketCreatureTeam.get(Integer.parseInt(selection)-1).getName());
//                    editPocketCreature(pocketCreatureTeam.get(Integer.parseInt(selection)-1));
//                    break;
//                case "0":
//                    exitMenu = true;
//                    break;
//                default:
//                    System.out.println("No \"" + selection + "\" option. Please select from the following." );
//                    break;
//            }
//        }
    }

    public void createPocketCreature(){
//        boolean exitMenu = false;
//
//        while(!exitMenu) {
//            System.out.println("""
//                    ---------------
//                    Which type of pocket creature would you like to add to your team:
//                    1: Electric Rat
//                    2: Fire Lizard
//                    3: Flower Dino
//                    4: Weird turtle
//                    0: cancel
//                    ---------------"""
//            );
//
//            System.out.print("Selection: ");
//            String selection = scan.nextLine().trim().toLowerCase();
//            System.out.println("What is your creatures name?");
//
//            switch (selection) {
//                case "1":
//                    ElectricRat electricRat = new ElectricRat(scan.nextLine().trim(), 1);
//                    electricRat.getAbilityList().add(tackle);
//                    electricRat.getAbilityList().add(shock);
//                    pocketCreatureTeam.add(electricRat);
//                    return;
//                case "2":
//                    FireLizard fireLizard = new FireLizard(scan.nextLine().trim(), 1);
//                    fireLizard.getAbilityList().add(tackle);
//                    fireLizard.getAbilityList().add(flamethrower);
//                    pocketCreatureTeam.add(fireLizard);
//                    return;
//                case "3":
//                    FlowerDino flowerDino = new FlowerDino(scan.nextLine().trim(), 1);
//                    flowerDino.getAbilityList().add(tackle);
//                    flowerDino.getAbilityList().add(razorLeaf);
//                    pocketCreatureTeam.add(flowerDino);
//                    return;
//                case "4":
//                    WeirdTurtle weirdTurtle = new WeirdTurtle(scan.nextLine().trim(), 1);
//                    weirdTurtle.getAbilityList().add(tackle);
//                    weirdTurtle.getAbilityList().add(waterJet);
//                    pocketCreatureTeam.add(weirdTurtle);
//                    return;
//                case "0":
//                    exitMenu = true;
//                    break;
//                default:
//                    System.out.println("\"" + selection + "\" is not a valid option." );
//                    break;
//            }
//        }
    }

    public void editPocketCreature(Creature creature) {
//        boolean exitMenu = false;
//        System.out.println("Here's where you would edit " + monster.getName() + "'s abilities.");
//
//        while(!exitMenu) {
//            System.out.println("---------------\n"
//                    + "What would you like to change about " + monster.getName() + "?\n"
//                    + "1: Change name\n"
//                    + "2: Add ability\n"
//                    + "3: Remove ability\n"
//                    + "4: Remove from team\n"
//                    + "0: back\n"
//                    + "---------------"
//            );
//
//            System.out.print("Selection: ");
//            String selection = scan.nextLine().trim().toLowerCase();
//
//            switch (selection) {
//                case "1":
//                    //Change name
//                    System.out.println("What is " + monster.getName() + "'s new name?");
//                    monster.setName(scan.nextLine().trim());
//                    break;
//                case "2":
//                    //Add ability
//                    System.out.println("What ability would you like to give " + monster.getName() + "?");
//                    //List every available move for the user to select from. Make sure there's not 4 or more already
//                    System.out.println("Adding " + scan.nextLine().trim() + " to " + monster.getName());
//                    break;
//                case "3":
//                    //Remove ability
//                    System.out.println("What ability would you like to remove from " + monster.getName() + "?");
//                    //List moves currently assigned to the creature.
//                    System.out.println("Removing " + scan.nextLine().trim() + " from " + monster.getName());
//                    break;
//                case "4":
//                    //Remove from team
//                    System.out.println("Are you sure you want to remove " + monster.getName() + " from your team?");
//                    System.out.println("Yes/No");
//                    selection = scan.nextLine().trim();
//                    if (selection.equalsIgnoreCase("yes") || selection.equalsIgnoreCase("y")) {
//                        pocketCreatureTeam.remove(monster);
//                        System.out.println("Say farewell to " + monster.getName() + "!");
//                        return;
//                    }
//                    break;
//                case "0":
//                    exitMenu = true;
//                    break;
//                default:
//                    System.out.println("\"" + selection + "\" is not a valid option." );
//                    break;
//            }
//        }
    }

    public void trainTeam() {
//        boolean exitMenu = false;
//        System.out.println("Team Training!");
//
//        while(!exitMenu) {
//            displayTeam("Select a team member to train:");
//            System.out.print("Selection: ");
//            String selection = scan.nextLine().trim().toLowerCase();
//
//            if ((pocketCreatureTeam.size() < Integer.parseInt(selection)) && (Integer.parseInt(selection) < 7)) {
//                System.out.println("Team slot is empty." );
//                continue;
//            }
//
//            switch (selection) {
//                case "1": case "2": case "3": case "4": case "5": case "6":
//                    System.out.println("You selected " + pocketCreatureTeam.get(Integer.parseInt(selection)-1).getName());
//                    trainPocketCreature(pocketCreatureTeam.get(Integer.parseInt(selection)-1));
//                    break;
//                case "0":
//                    exitMenu = true;
//                    break;
//                default:
//                    System.out.println("No \"" + selection + "\" option. Please select from the following." );
//                    break;
//            }
//        }
    }

    public void trainPocketCreature(Creature creature) {
        creature.gainExperience(8);
    }

    public void displayTeam(String prompt) {
//        System.out.println("---------------\n"
//                + prompt
//        );
//        for (int i = 0; i < 6; i++) {
//            if (pocketCreatureTeam.size() > i) {
//                System.out.println("Slot " + (i+1) + ": " + pocketCreatureTeam.get(i).getName() + " [Level: " + pocketCreatureTeam.get(i).getLevel() + "]");
//                System.out.println("    Health:  " + pocketCreatureTeam.get(i).getHealthStat() + " Attack: " + pocketCreatureTeam.get(i).getAttackStat());
//                System.out.println("    Defense: " + pocketCreatureTeam.get(i).getDefenseStat() + " Speed:  " + pocketCreatureTeam.get(i).getSpeedStat());
//            }
//            else {
//                System.out.println("Slot " + (i+1) + ": [Empty team slot]");
//            }
//        }
//        System.out.println("0: back");
//        System.out.println("---------------");
    }

    public void displayBattleOptions(Creature creature) {
//        System.out.println("---------------\n"
//                + "Select an ability"
//        );
//        for (int i = 0; i < monster.getAbilityList().size() ; i++) {
//            System.out.println((i+1) + ": " + monster.getAbilityList().get(i).getAbilityName());
//        }
//        System.out.println("0: Run from battle");
//        System.out.println("---------------");
    }
}
