package com.example.andoid_labassignment.ui.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.andoid_labassignment.Abstract.BaseMapFragment;
import com.example.andoid_labassignment.BackgroundFetch.GetPlaces;
import com.example.andoid_labassignment.Database.PlaceServiceImpl;
import com.example.andoid_labassignment.Helper.Helper;
import com.example.andoid_labassignment.Models.Place;
import com.example.andoid_labassignment.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapFragment extends BaseMapFragment implements GoogleMap.OnMarkerClickListener {

    private PlaceServiceImpl placeService;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        if (!checkPermission()) {
            requestPermission();
        } else {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflateFragment(R.layout.fragment_map, inflater ,container);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeService = new PlaceServiceImpl(getContext());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restaurant:
                searchNearBy("restaurant");
                break;
            case R.id.action_cafe:
                searchNearBy("cafe");
                break;
            case R.id.action_museums:
                searchNearBy("museum");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchNearBy(String type) {
        Object[] data = new Object[3];
        data[0] = mMap;
        data[1] = new LatLng(latitude, longitude);
        data[2] = type;
        GetPlaces getPlaces = new GetPlaces();
        getPlaces.execute(data);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showAlert(getContext(), marker);
        return false;
    }

    private void addToVisit(Marker marker) {
        String[] title = marker.getTitle().split(" : ");
        String name = title[0];
        String vicinity = title[1];
        String lat = String.valueOf(marker.getPosition().latitude);
        String lng = String.valueOf(marker.getPosition().longitude);
        Place place = new Place(name, vicinity, lat, lng);
        placeService.insertAll(place);
        Helper.showAlert(getContext(), "Alert!", "Saved successfully.");
    }

    private void showAlert(Context context, final Marker marker) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage("Do you want to visit this place?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToVisit(marker);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
