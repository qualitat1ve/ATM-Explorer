package com.atmexplorer.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;
import java.util.List;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief
 */
public class GeoUtils {

    private static GeoUtils mInstance = null;
    private static Geocoder mGeocoder = null;

    public static GeoUtils getInstance(Context context) {
        if(mInstance ==null) {
            mInstance = new GeoUtils(context);
        }
        return mInstance;
    }

    private GeoUtils(Context context){
        mGeocoder = new Geocoder(context);
    }


    public Pair<Double, Double> getCoordinates(String address) throws IOException {
        List<Address> list = mGeocoder.getFromLocationName(address, 1);
        double latitude  = 0.d;
        double longitude = 0.d;
        if(!list.isEmpty()) {
            latitude =  list.get(0).getLatitude();
            longitude = list.get(0).getLongitude();
        }
        return new Pair<Double,Double>(latitude, longitude);
    }
}
