package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.AbilityEntity;

import java.util.List;
import java.util.Map;

@Dao
public interface AbilityDAO {

    @Query("SELECT * FROM " + ApplicationDatabase.ABILITY_TABLE + " WHERE abilityID = :id")
    AbilityEntity getAbilityById(String id);

    @Query("SELECT * FROM " + ApplicationDatabase.ABILITY_TABLE + " WHERE abilityID IN (:ids)")
    List<AbilityEntity> getAbilitiesByIds(List<String> ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AbilityEntity> abilities);

    @Query("SELECT * FROM abilityTable")
    List<AbilityEntity> getAll();
}
