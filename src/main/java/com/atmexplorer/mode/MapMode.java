package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
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
    private LocationTracker mLocationTracker;
    private DataManager mDataManager;

    public MapMode(DataManager dataManager, LocationTracker locationTracker) {
        super();
        mLocationTracker = locationTracker;
        mDataManager = dataManager;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(inflater, viewGroup, bundle);
        setup();
        return view;
    }

    @Override
    public void onChangeState(ActiveState state) {
        switch (state) {
            case ACTIVE:
                break;
            case INACTIVE:
                break;
            default:
                break;
        }
    }

    private void setup() {
        GoogleMap mGoogleMap = getMap();
        if (mGoogleMap == null) {
            return;
        }

        ATMItem currentItem = mDataManager.getCurrentItem();
        if(currentItem !=null) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(currentItem.getLatitude(), currentItem.getLongitude())).
                    title(currentItem.getBankName() + ", " + currentItem.getAddress());
            mGoogleMap.addMarker(marker);
            CameraPosition position = new CameraPosition.Builder().target(new LatLng(currentItem.getLatitude(), currentItem.getLongitude())).
                    zoom(MapMode.ZOOM_LEVEL).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);


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
