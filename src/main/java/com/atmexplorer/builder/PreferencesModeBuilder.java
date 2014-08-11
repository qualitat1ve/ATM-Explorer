package com.atmexplorer.builder;

import android.view.View;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.SettingsModeFragment;
import com.atmexplorer.mode.Mode;
import com.atmexplorer.mode.ModesManager;
import com.atmexplorer.mode.PreferencesMode;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Creates PreferencesFragment
 */
public class PreferencesModeBuilder extends ModeBuilder {

    public PreferencesModeBuilder(View rootView, SharedData sharedData, ModesManager.ModeChangeRequester modeChangeRequester) {
        super(rootView, sharedData, modeChangeRequester);
    }

    @Override
    public Mode build() {
        PreferencesMode preferencesMode = new PreferencesMode(mSharedData, new SettingsModeFragment());
        return preferencesMode;
    }
}
