package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.atmexplorer.SharedData;
import com.atmexplorer.R;
import com.atmexplorer.content.SettingsModeFragment;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that contains application's settings
 */
public class PreferencesMode extends BaseMode {

    public PreferencesMode(SharedData sharedData, SettingsModeFragment fragment) {
        super(sharedData, fragment);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void setupMode() {

    }

    @Override
    protected void deactivateMode() {

    }
}
