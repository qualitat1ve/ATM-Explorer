package com.atmexplorer.builder;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.atmexplorer.R;
import com.atmexplorer.SharedData;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.content.MainModeFragment;
import com.atmexplorer.mode.MainMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build list mode
 */
public class MainModeBuilder extends ModeBuilder {

    private MainModeFragment mMainModeFragment;

    public MainModeBuilder(View rootView, SharedData sharedData, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, sharedData, modeChangeRequester);
        mMainModeFragment = new MainModeFragment(locationTracker, modeChangeRequester, sharedData);


    }

    @Override
    public Mode build() {
        MainMode mainMode = new MainMode(mSharedData, mMainModeFragment);
        return mainMode;
    }
}
