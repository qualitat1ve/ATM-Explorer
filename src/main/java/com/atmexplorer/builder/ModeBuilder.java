package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.DataManager;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Base class for build mode
 */
public abstract class  ModeBuilder {

    protected  View mRootView;
    protected  DataManager mDataManager = null;
    protected  ModesManager.ModeChangeRequester mModeChangeRequester = null;
    /**
     * Constructor
     * @param dataManager - data manager
     */
    ModeBuilder(View rootView, DataManager dataManager, ModesManager.ModeChangeRequester modeChangeRequester) {
        mRootView = rootView;
        mDataManager = dataManager;
        mModeChangeRequester = modeChangeRequester;
    }

    /**
     * @return built by mode,<br/>
     * and when the attempt to create a mode fails - return NULL.
     */
    public abstract Mode build();
}
