package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.Data;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Base class for build mode
 */
public abstract class  ModeBuilder {

    protected  View mRootView;
    protected Data mData = null;
    protected  ModesManager.ModeChangeRequester mModeChangeRequester = null;
    /**
     * Constructor
     * @param data - data for share between modes
     */
    ModeBuilder(View rootView, Data data, ModesManager.ModeChangeRequester modeChangeRequester) {
        mRootView = rootView;
        mData = data;
        mModeChangeRequester = modeChangeRequester;
    }

    /**
     * @return built by mode,<br/>
     * and when the attempt to create a mode fails - return NULL.
     */
    public abstract Mode build();
}
