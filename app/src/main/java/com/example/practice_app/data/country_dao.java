package com.example.practice_app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.practice_app.model.country;

import java.util.List;
@Dao
public interface country_dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(country country);
    @Query("DELETE FROM country_table")
    void delete_all();
    @Query("SELECT * FROM country_table")
    LiveData<List<country>> get_all_country();
}
