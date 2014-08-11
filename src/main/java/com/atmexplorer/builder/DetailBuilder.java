package com.atmexplorer.builder;


import android.view.View;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.DetailModeFragment;
import com.atmexplorer.mode.DetailMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build detail mode
 */
public class DetailBuilder extends ModeBuilder {

    private DetailModeFragment mDetailModeFragment;

    public DetailBuilder(View rootView, SharedData sharedData, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, sharedData, modeChangeRequester);
        mDetailModeFragment = new DetailModeFragment(rootView, modeChangeRequester, sharedData);
    }

    @Override
    public Mode build() {
        return new DetailMode(mSharedData, mDetailModeFragment);
    }
}
