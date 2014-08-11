package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.MapModeFragment;
import com.atmexplorer.mode.MapMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build map mode
 */
public class MapModeBuilder extends  ModeBuilder {

    private MapModeFragment mFragment;

    public MapModeBuilder(View rootView, SharedData sharedData, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, sharedData, modeChangeRequester);
        mFragment = new MapModeFragment(modeChangeRequester, sharedData);
    }

    @Override
    public Mode build() {
        MapMode mapMode = new MapMode(mSharedData, mFragment);
        return mapMode;
    }
}
