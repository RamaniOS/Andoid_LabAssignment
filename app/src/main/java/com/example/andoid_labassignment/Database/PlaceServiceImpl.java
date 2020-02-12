package com.example.andoid_labassignment.Database;

import android.content.Context;

import com.example.andoid_labassignment.Models.Place;

import java.util.List;

public class PlaceServiceImpl implements PlaceService {

    private PlaceDao placeDao;

    public PlaceServiceImpl(Context context) {
        placeDao = AppDatabase.getInstance(context).placeDao();
    }

    @Override
    public List<Place> getAll() {
        return placeDao.getPlaces();
    }

    @Override
    public void insertAll(Place... places) {
        placeDao.insertAll(places);
    }

    @Override
    public void delete(Place place) {
        placeDao.delete(place);
    }

    @Override
    public void update(Place place) {
        placeDao.update(place);
    }
}
