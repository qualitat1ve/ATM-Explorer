package com.atmexplorer.builder;


import com.atmexplorer.DataManager;
import com.atmexplorer.mode.DetailMode;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for build detail mode
 */
public class DetailBuilder extends  ModeBuilder {

    public DetailBuilder(DataManager dataManager, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(dataManager, modeChangeRequester);
    }

    @Override
    public Mode build() {
        return new DetailMode();
    }
}
