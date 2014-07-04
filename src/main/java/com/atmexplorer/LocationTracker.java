package com.atmexplorer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class which provides access to GPS data
 */

public class LocationTracker extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1;

    private final Context mContext;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location mLocation;
    private double mLatitude;
    private double mLongitude;
    private LocationManager mLocationManager;

    public LocationTracker(Context context) {
        mContext = context;
        calculateLocation();
    }

    private void calculateLocation() {
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        String providerName = null;
        if (isNetworkEnabled) {
            providerName = LocationManager.NETWORK_PROVIDER;
        } else if (isGPSEnabled) {
            providerName = LocationManager.GPS_PROVIDER;
        } else {
            String errorMessage = getResources().getString(R.string.unable_location_service_message);
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        obtainCoordinates(providerName);
    }

    private void obtainCoordinates(String provider) {
        mLocationManager.requestLocationUpdates(provider, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
        mLocation = mLocationManager.getLastKnownLocation(provider);
        mLatitude = mLocation.getLatitude();
        mLongitude = mLocation.getLongitude();
    }

    public final double getLatitude() {
        return mLatitude;
    }

    public final double getLongitude() {
        return mLongitude;
    }

    public final Location getLocation() {
        return mLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        //unused callback
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //unused callback
    }

    @Override
    public void onProviderEnabled(String s) {
        //unused callback
    }

    @Override
    public void onProviderDisabled(String s) {
        //unused callback
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
