package com.example.andoid_labassignment.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andoid_labassignment.Models.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Insert
    void insertAll(Place... places);

    @Delete
    void delete(Place place);

    @Update
    void update(Place place);

    @Query("SELECT * from place_table ORDER BY name ASC")
    List<Place> getPlaces();
}
