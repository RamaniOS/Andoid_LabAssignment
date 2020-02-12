package com.example.andoid_labassignment.ui.menu;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import com.example.andoid_labassignment.Abstract.BaseMapFragment;
import com.example.andoid_labassignment.BackgroundFetch.GetDistance;
import com.example.andoid_labassignment.Database.PlaceServiceImpl;
import com.example.andoid_labassignment.Helper.Helper;
import com.example.andoid_labassignment.Models.Place;
import com.example.andoid_labassignment.R;
import com.example.andoid_labassignment.Store.FetchAddressStore;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailMapFragment extends BaseMapFragment implements GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private static Place place = null;
    private static boolean isEdit = false;
    private PlaceServiceImpl placeService;
    private LatLng[] locations = new LatLng[2];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflateFragment(R.layout.fragment_detail_map, inflater ,container);
        placeService = new PlaceServiceImpl(getContext());
        Bundle args = getArguments();
        place = args.getParcelable(getClass().getName());
        isEdit = args.getBoolean("isEdit");
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        if (!checkPermission()) {
            requestPermission();
        } else {
            mMap.setMyLocationEnabled(true);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentLocationMarker(place);
            }
        }, 1000);
    }

    private void setCurrentLocationMarker(Place place) {
        MarkerOptions markerOptions = new MarkerOptions().position(place.getLocation())
                .title(place.getName() + " : "+ place.getVicinity())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .draggable(isEdit);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {}

    @Override
    public void onMarkerDrag(Marker marker) {}

    @Override
    public void onMarkerDragEnd(Marker marker) {
        place.setLatitude(String.valueOf(marker.getPosition().latitude));
        place.setLongitude(String.valueOf(marker.getPosition().longitude));
        place.setName("N/A");
        place.setVisited(false);
        place.setVicinity(FetchAddressStore.execute(getContext(), marker.getPosition()));
        placeService.update(place);
        Helper.showAlert(getContext(), "Alert!", "Place updated successfully.");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (isEdit) {return false;}
        place.setVisited(true);
        placeService.update(place);
        Helper.showAlert(getContext(), place.getName() + " : " + place.getVicinity(), "You have visited this place.");
        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_direction, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_direction:
                calculateDistance();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateDistance() {
        locations[0] = new LatLng(latitude, longitude);
        locations[1] = place.getLocation();
        Object[] objects = new Object[3];
        objects[0] = mMap;
        objects[1] = this.getContext();
        objects[2] = locations;
        GetDistance distance = new GetDistance();
        distance.execute(objects);
    }
}
