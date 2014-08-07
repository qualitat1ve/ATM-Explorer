package com.atmexplorer.builder;

import android.content.Context;
import android.view.View;
import com.atmexplorer.Data;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.mode.MapMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build map mode
 */
public class MapModeBuilder extends  ModeBuilder {

    public MapModeBuilder(View rootView, Data data, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, data, modeChangeRequester);
    }

    @Override
    public Mode build() {
        MapMode mapFragment = new MapMode(mData);
        mapFragment.setRetainInstance(true);
        return mapFragment;
    }
}
