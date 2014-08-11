package com.atmexplorer.content;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.SharedData;
import com.atmexplorer.R;
import com.atmexplorer.mode.MapMode;
import com.atmexplorer.mode.ModesManager;
import com.atmexplorer.model.ATMItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that presents Map Screen
 */
public class MapModeFragment extends Fragment {

    private MapView mMapView;
    private ModesManager.ModeChangeRequester mModeChangeRequester;
    private SharedData mSharedData;

    public MapModeFragment(ModesManager.ModeChangeRequester requesting, SharedData data) {
        mModeChangeRequester = requesting;
        mSharedData = data;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.map_layout, viewGroup, false);
        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(bundle);
        setupMap();
        return view;
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

    private void setupMap() {
        GoogleMap mGoogleMap = mMapView.getMap();
        if (mGoogleMap == null) {
            return;
        }
        MapsInitializer.initialize(getActivity());

        ATMItem currentItem = mSharedData.getCurrentItem();
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
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                mModeChangeRequester.onModeChange(ModesManager.ModeIndex.DETAIL, false);
            }
        });
    }
}
