package com.atmexplorer.builder;

import android.content.Context;
import android.view.View;
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
public class MapModeBuilder extends  ModeBuilder {

    private MapMode mMapFragment;
    private Context mContext;
    private LocationTracker mLocationTracker;
    private DataManager mDataManager;


    public MapModeBuilder(View rootView, DataManager dataManager, Context context, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, dataManager, modeChangeRequester);
        mContext = context;
        mLocationTracker = locationTracker;
        mDataManager = dataManager;
    }

    @Override
    public Mode build() {
        mMapFragment = new MapMode(mDataManager, mLocationTracker);
        mMapFragment.setRetainInstance(true);
        return mMapFragment;
    }
}
