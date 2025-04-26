package com.example.project2.database;
/**
 * Name: Austin Shatswell
 * Date: 4/27/25
 * Explanation: Project 2: Creature Coliseum
 *  used for dealing with Creature tables
 */

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.CreatureEntity;

import java.util.List;

@Dao
public interface CreatureDAO {
    /**
     * used to insert and/or replace creature entities in the database
     * @param creature
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CreatureEntity creature);

    /**
     * used to insert and/or replace a list of creature entities in the database
     * @param creatureEntities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreatureEntity> creatureEntities);

    /**
     * used to retrieve a creature by its unique id. will be
     * mostly used when retrieving template creatures
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE creatureId = :id")
    CreatureEntity getCreatureById(int id);

    /**
     * Used to retrieve template creatures by their creature type
     * ended up not really needing this but could be useful if
     * I were to expand on this project
     * @param type
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = 'NONE' AND type = :type")
    CreatureEntity getCreatureTemplateByType(String type);

    /**
     * used to retrieve a list of creature associated with
     * a users unique ID. used when loading a users team
     * @param userId
     * @return
     */
    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = :userId")
    List<CreatureEntity> getCreaturesByUserId(String userId);

    /**
     * used to delete all creatures of a specific user
     * @param userId
     */
    @Query("DELETE FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = :userId")
    void deleteAllCreaturesByUserId(String userId);

    @androidx.room.Update
    void update(CreatureEntity creature);

    @androidx.room.Delete
    void delete(CreatureEntity creature);
}
