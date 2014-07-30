package com.atmexplorer.builder;

import com.atmexplorer.DataManager;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Base class for build mode
 */
public abstract class  ModeBuilder {

    protected DataManager mDataManager = null;
    protected  ModesManager.ModeChangeRequester mModeChangeRequester = null;
    /**
     * Constructor
     * @param dataManager - data manager
     */
    ModeBuilder(DataManager dataManager, ModesManager.ModeChangeRequester modeChangeRequester) {
        mDataManager = dataManager;
        mModeChangeRequester = modeChangeRequester;
    }

    /**
     * @return built by mode,<br/>
     * and when the attempt to create a mode fails - return NULL.
     */
    public abstract Mode build();
}
