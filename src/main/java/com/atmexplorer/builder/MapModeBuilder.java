package com.atmexplorer.builder;

import android.content.Context;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.mode.MapMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;
import com.atmexplorer.model.ATMItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build map mode
 */
public class MapModeBuilder extends  ModeBuilder implements MapMode.OnMapReadyListener {

    private MapMode mMapFragment;
    private Context mContext;
    private LocationTracker mLocationTracker;
    private DataManager mDataManager;


    public MapModeBuilder(DataManager dataManager, Context context, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(dataManager, modeChangeRequester);
        mContext = context;
        mLocationTracker = locationTracker;
        mDataManager = dataManager;
    }

    @Override
    public Mode build() {
        mMapFragment = new MapMode(mDataManager, this, mLocationTracker);
        mMapFragment.setRetainInstance(true);
        return mMapFragment;
    }

    @Override
    public void onMapReady() {
        mMapFragment.setup();
    }
}
