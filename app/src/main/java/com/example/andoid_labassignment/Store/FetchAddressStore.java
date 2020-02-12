package com.example.andoid_labassignment.Store;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressStore {

    public static String execute(Context context, LatLng location) {
        StringBuilder addressName = new StringBuilder();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses =  geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getSubThoroughfare() != null) {
                    addressName.append(address.getSubThoroughfare());
                }
                if (address.getThoroughfare() != null) {
                    addressName.append(" " + address.getThoroughfare());
                }
                if (address.getLocality() != null) {
                    addressName.append(", " + address.getLocality());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressName.toString();
    }
}
