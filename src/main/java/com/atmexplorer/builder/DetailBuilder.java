package com.atmexplorer.builder;


import android.view.View;
import com.atmexplorer.DataManager;
import com.atmexplorer.mode.DetailMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build detail mode
 */
public class DetailBuilder extends  ModeBuilder {

    public DetailBuilder(View rootView, DataManager dataManager, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, dataManager, modeChangeRequester);
    }

    @Override
    public Mode build() {
        return new DetailMode(mRootView, mDataManager, mModeChangeRequester);
    }
}
