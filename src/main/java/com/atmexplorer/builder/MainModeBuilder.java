package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.CustomATMLoader;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.SharedData;
import com.atmexplorer.adapter.ATMItemListAdapter;
import com.atmexplorer.content.MainModeFragment;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.mode.MainMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build list mode
 */
public class MainModeBuilder extends ModeBuilder {

    private MainModeFragment mMainModeFragment;

    public MainModeBuilder(View rootView, SharedData sharedData, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester, MainModeFragment.LoaderProvider loader) {
        super(rootView, sharedData, modeChangeRequester);

        ATMItemListAdapter  itemAdapter = new ATMItemListAdapter(rootView.getContext(), locationTracker);
        mMainModeFragment = new MainModeFragment(itemAdapter, modeChangeRequester, sharedData, loader);
    }

    @Override
    public Mode build() {
        MainMode mainMode = new MainMode(mSharedData, mMainModeFragment);
        return mainMode;
    }
}
