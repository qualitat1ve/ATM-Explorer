package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.model.ATMItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Maks Kukushkin(maks.kukushkin@gmail.com)
 * @brief MapMode wrapper which provides callback that
 * notifies subscribers that fragment view already created
 */
public class MapMode extends MapFragment implements Mode {
    public static final int ZOOM_LEVEL = 13;
    private OnMapReadyListener mOnMapReadyListener;
    private LocationTracker mLocationTracker;
    private DataManager mDataManager;

    public MapMode(DataManager dataManager, OnMapReadyListener listener, LocationTracker locationTracker) {
        super();
        mOnMapReadyListener = listener;
        mLocationTracker = locationTracker;
        mDataManager = dataManager;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(inflater, viewGroup, bundle);
        if (mOnMapReadyListener != null){
            mOnMapReadyListener.onMapReady();
        }
        return view;
    }

    public interface OnMapReadyListener {
        void onMapReady();
    }

    @Override
    public void onChangeState(ActiveState state) {
        switch (state) {
            case ACTIVE:
                setup();
                break;
            case INACTIVE:
                break;
            default:
                break;
        }
    }

    public void setup() {
        GoogleMap mGoogleMap = getMap();
        if (mGoogleMap == null) {
            return;
        }

        double latitude = mLocationTracker.getLatitude();
        double longitude = mLocationTracker.getLongitude();

        for (ATMItem item : mDataManager.getItems()) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).
                    title(item.getBankName() + ", " + item.getAddress());
            mGoogleMap.addMarker(marker);
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        CameraPosition position = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).
                zoom(MapMode.ZOOM_LEVEL).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    @Override
    public Fragment getModeFragment() {
        return this;
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {

    }
}
