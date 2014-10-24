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
    private SettingsModeFragment.SettingsListener mListener;

    public PreferencesModeBuilder(View rootView, SharedData sharedData, ModesManager.ModeChangeRequester modeChangeRequester, SettingsModeFragment.SettingsListener listener) {
        super(rootView, sharedData, modeChangeRequester);
        mListener = listener;
    }

    @Override
    public Mode build() {
        PreferencesMode preferencesMode = new PreferencesMode(mSharedData, new SettingsModeFragment(mListener));
        return preferencesMode;
    }
}
