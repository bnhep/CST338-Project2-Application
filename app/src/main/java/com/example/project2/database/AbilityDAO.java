package com.example.project2.database;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  used for dealing with Ability table
 */

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.AbilityEntity;

import java.util.List;

@Dao
public interface AbilityDAO {

    /**
     * used to retrieve an ability based on its unique ID
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.ABILITY_TABLE + " WHERE abilityID = :id")
    AbilityEntity getAbilityById(String id);

    /**
     * used to retrieve a list of abilities by ID. did not end
     * up using this. could be useful if expanding on the project
     * @param ids
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.ABILITY_TABLE + " WHERE abilityID IN (:ids)")
    List<AbilityEntity> getAbilitiesByIds(List<String> ids);

    /**
     * used to insert a list of abilities into the Ability table. used
     * on app startup to created a database of default abilities
     * @param abilities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AbilityEntity> abilities);

    /**
     * used to retrieve a list of all abilities in the table
     * @return
     */
    @Query("SELECT * FROM abilityTable")
    List<AbilityEntity> getAll();

    @androidx.room.Update
    void update(AbilityEntity ability);

    @androidx.room.Delete
    void delete(AbilityEntity ability);
}
