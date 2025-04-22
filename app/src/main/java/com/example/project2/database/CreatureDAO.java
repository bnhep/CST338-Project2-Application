package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.CreatureEntity;

import java.util.List;

@Dao
public interface CreatureDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CreatureEntity creature);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreatureEntity> creatureEntities);

    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE creatureId = :id")
    CreatureEntity getCreatureById(String id);

    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = 'NONE' AND type = :type")
    CreatureEntity getCreatureTemplateByType(String type);

    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = :userId")
    List<CreatureEntity> getCreaturesByUserId(String userId);

    @Query("DELETE FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE userId = :userId")
    void deleteAllCreaturesByUserId(String userId);
}
