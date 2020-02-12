package com.example.andoid_labassignment.BackgroundFetch;

import android.os.AsyncTask;

import com.example.andoid_labassignment.Models.Place;
import com.example.andoid_labassignment.Store.GoogleAPIStore;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public class GetPlaces extends AsyncTask<Object, List<Place>, List<Place>> {

    GoogleMap mMap;
    LatLng location;

    @Override
    protected List<Place> doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        List<Place> places = null;
        try {
            location = (LatLng) objects[1];
            String type = (String) objects[2];
            places = GoogleAPIStore.fetchPlaces(location, type);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<Place> places) {
        super.onPostExecute(places);
        mMap.clear();
        //setCurrentLocationMarker();
        for (Place place: places) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(place.getLocation())
                    .title(place.getName() + " : " + place.getVicinity())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            mMap.addMarker(markerOptions);
        }
    }

    private void setCurrentLocationMarker() {
        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .snippet("You are here");
        mMap.addMarker(markerOptions);
    }
}

