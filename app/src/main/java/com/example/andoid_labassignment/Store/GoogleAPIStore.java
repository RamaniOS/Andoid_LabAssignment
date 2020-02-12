package com.example.andoid_labassignment.Store;

import com.example.andoid_labassignment.Models.Distance;
import com.example.andoid_labassignment.Models.Place;
import com.example.andoid_labassignment.Models.PolyLine;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GoogleAPIStore {

    private static final String RESULT_KEY = "results";
    private static final int RADIUS = 1500;
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final String API_KEY = "AIzaSyCGNZIaTG3Lj3gj5I6OrIvf9NZMQbz2oeU";

    public static List<Place> fetchPlaces(LatLng location, String type) throws JSONException, IOException {

        StringBuilder placeURL = new StringBuilder(BASE_URL+"place/nearbysearch/json?");
        placeURL.append("location="+location.latitude+","+location.longitude);
        placeURL.append("&radius="+RADIUS);
        placeURL.append("&type="+type);
        placeURL.append("&key="+API_KEY);

        String jsonData = BaseAPIStore.request(placeURL.toString());
        List<Place> places = new ArrayList<>();
        if (jsonData != null) {
            JSONArray jsonArray;
            JSONObject json = new JSONObject(jsonData);
            jsonArray = json.getJSONArray(RESULT_KEY);
            // Iteration of json array
            for (int i = 0; i<jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    places.add(new Place(jsonObject));
                } catch (JSONException e) {
                    e.getStackTrace();
                }
            }
            return  places;
        } else {
            return places;
        }
    }

    public static Distance fetchDistance(LatLng location, LatLng dest_location) throws JSONException, IOException {
        StringBuilder url = new StringBuilder(BASE_URL+"directions/json?");
        url.append("origin="+location.latitude+","+location.longitude);
        url.append("&destination="+dest_location.latitude+","+dest_location.longitude);
        url.append("&key="+API_KEY);

        String jsonData = BaseAPIStore.request(url.toString());
        Distance distance;
        if (jsonData != null) {
            JSONObject json = new JSONObject(jsonData);
            distance = new Distance(json);
            return  distance;
        }
        return null;
    }

    public static PolyLine fetchDirections(LatLng location, LatLng dest_location) throws JSONException, IOException {
        StringBuilder url = new StringBuilder(BASE_URL+"directions/json?");
        url.append("origin="+location.latitude+","+location.longitude);
        url.append("&destination="+dest_location.latitude+","+dest_location.longitude);
        url.append("&key="+API_KEY);

        String jsonData = BaseAPIStore.request(url.toString());
        PolyLine polyLine;
        if (jsonData != null) {
            JSONObject json = new JSONObject(jsonData);
            polyLine = new PolyLine(json);
            return  polyLine;
        }
        return null;
    }
}
