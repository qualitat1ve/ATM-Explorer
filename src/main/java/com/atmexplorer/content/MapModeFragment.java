package com.atmexplorer.content;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.SharedData;
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

import java.util.Set;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that presents Map Screen
 */
public class MapModeFragment extends Fragment {

    private MapView mMapView;
    private ModesManager.ModeChangeRequester mModeChangeRequester;
    private SharedData mSharedData;
    private LocationTracker mLocationTracker;

    public MapModeFragment(ModesManager.ModeChangeRequester requesting, SharedData data, LocationTracker locationTracker) {
        mModeChangeRequester = requesting;
        mSharedData = data;
        mLocationTracker = locationTracker;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.layout_map, viewGroup, false);
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
        }else {
            Set<ATMItem> items = mSharedData.getItems();
            Log.i("map", "items " + items.size());
            for(ATMItem item :items) {
                MarkerOptions marker = new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).
                        title(item.getBankName() + ", " + item.getAddress());
                mGoogleMap.addMarker(marker);
            }
            CameraPosition position = new CameraPosition.Builder().target(new LatLng(mLocationTracker.getLocation().getLatitude(), mLocationTracker.getLocation().getLongitude())).
                    zoom(MapMode.ZOOM_LEVEL).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //need find current item by position(latitude and longitude)
                mModeChangeRequester.onModeChange(ModesManager.ModeIndex.DETAIL, false);
            }
        });
    }
}
