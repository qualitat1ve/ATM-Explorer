package com.atmexplorer.content;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.atmexplorer.R;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that presents Settings Screen
 */
public class SettingsModeFragment extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
