package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.mode.ListMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build list mode
 */
public class ListModeBuilder extends ModeBuilder {

    private ListMode mATMListFragment = null;
    private LocationTracker mLocationTracker;

    public ListModeBuilder(View rootView, DataManager dataManager, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, dataManager, modeChangeRequester);
        mLocationTracker = locationTracker;
    }

    @Override
    public Mode build() {
        mATMListFragment = new ListMode(mDataManager, mLocationTracker, mModeChangeRequester);

        return mATMListFragment;
    }
}
