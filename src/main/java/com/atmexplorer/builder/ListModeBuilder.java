package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.Data;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.mode.MainMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build list mode
 */
public class ListModeBuilder extends ModeBuilder {

    private LocationTracker mLocationTracker;

    public ListModeBuilder(View rootView, Data data, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, data, modeChangeRequester);
        mLocationTracker = locationTracker;
    }

    @Override
    public Mode build() {
        MainMode mode = new MainMode(mData, mLocationTracker, mModeChangeRequester);
        return mode;
    }
}
