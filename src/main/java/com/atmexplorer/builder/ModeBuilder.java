package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.SharedData;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Base class for build mode
 */
public abstract class ModeBuilder {

    protected  View mRootView;
    protected SharedData mSharedData = null;
    protected  ModesManager.ModeChangeRequester mModeChangeRequester = null;
    /**
     * Constructor
     * @param sharedData - sharedData for share between modes
     */
    ModeBuilder(View rootView, SharedData sharedData, ModesManager.ModeChangeRequester modeChangeRequester) {
        mRootView = rootView;
        mSharedData = sharedData;
        mModeChangeRequester = modeChangeRequester;
    }

    /**
     * @return built by mode,<br/>
     * and when the attempt to create a mode fails - return NULL.
     */
    public abstract Mode build();
}
