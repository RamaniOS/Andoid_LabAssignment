package com.example.andoid_labassignment.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.andoid_labassignment.Abstract.BaseMapFragment;
import com.example.andoid_labassignment.BackgroundFetch.GetDistance;
import com.example.andoid_labassignment.Helper.Helper;
import com.example.andoid_labassignment.R;
import com.example.andoid_labassignment.Store.FetchAddressStore;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DistanceFragment extends BaseMapFragment {

    private List<Marker> markersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflateFragment(R.layout.fragment_distance, inflater ,container);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                dest_lat = latLng.latitude;
                dest_lng =latLng.longitude;
                setMarker(latLng);
            }
        });
    }

    private void setMarker(LatLng location) {
        int count = markersList.size();
        if (count >=2) {return;}
        String title = FetchAddressStore.execute(getContext(), location);
        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true);
        Marker marker = mMap.addMarker(markerOptions);
        markersList.add(marker);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_distance, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_distance:
                if (markersList.size() == 2) {
                    calculateDistance();
                } else {
                    Helper.showAlert(getContext(), "Error", "Please drop two markers on map for further actions.");
                }
                break;
            case R.id.action_clear:
                mMap.clear();
                markersList.removeAll(markersList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateDistance() {
        Object[] objects = new Object[3];
        objects[0] = mMap;
        objects[1] = this.getContext();
        LatLng[] locations = new LatLng[markersList.size()];
        for (int i=0;i<markersList.size();i++) {
            locations[i] = markersList.get(i).getPosition();
        }
        objects[2] = locations;
        GetDistance distance = new GetDistance();
        distance.execute(objects);
    }
}


