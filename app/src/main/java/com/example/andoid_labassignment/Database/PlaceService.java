package com.example.andoid_labassignment.Database;

import com.example.andoid_labassignment.Models.Place;

import java.util.List;

public interface PlaceService {

    List<Place> getAll();

    void insertAll(Place... places);

    void delete(Place place);

    void update(Place place);
}

