package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.Data;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.model.ATMItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Maks Kukushkin(maks.kukushkin@gmail.com)
 * @brief Fragment which contains MapView
 */
public class MapMode extends BaseMode {
    public static final int ZOOM_LEVEL = 13;
    private MapView mMapView;

    public MapMode(Data data) {
        super(data);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.map_layout, viewGroup, false);
        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(bundle);
        setupMap();
        return view;
    }

    private void setupMap() {
        GoogleMap mGoogleMap = mMapView.getMap();
        if (mGoogleMap == null) {
            return;
        }
        MapsInitializer.initialize(this.getActivity());

        ATMItem currentItem = mData.getCurrentItem();
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

    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public Fragment getModeFragment() {
        return this;
    }

    @Override
    protected void setupMode() {

    }

    @Override
    protected void deactivateMode() {

    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {

    }
}
