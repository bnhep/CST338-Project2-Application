package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.CreatureEntity;

@Dao
public interface CreatureDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CreatureEntity creature);

    @Query("SELECT * FROM " + ApplicationDatabase.CREATURE_TABLE + " WHERE creatureId = :id")
    CreatureEntity getCreatureById(String id);
}
