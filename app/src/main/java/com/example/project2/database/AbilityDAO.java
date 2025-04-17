package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.AbilityEntity;

import java.util.List;

@Dao
public interface AbilityDAO {

    @Query("SELECT * FROM abilities WHERE abilityID = :id")
    AbilityEntity getAbilityById(String id);

    @Query("SELECT * FROM abilities WHERE abilityID IN (:ids)")
    List<AbilityEntity> getAbilitiesByIds(List<String> ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AbilityEntity> abilities);
}
