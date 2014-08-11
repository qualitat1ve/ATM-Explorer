package com.atmexplorer.mode;

import android.content.Intent;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.MapModeFragment;

/**
 * @author Maks Kukushkin(maks.kukushkin@gmail.com)
 * @brief Fragment which contains MapView
 */
public class MapMode extends BaseMode {

    public static final int ZOOM_LEVEL = 13;
    private MapModeFragment mFragment;

    public MapMode(SharedData sharedData, MapModeFragment fragment) {
        super(sharedData, fragment);
        mFragment = fragment;
    }

    @Override
    protected void setupMode() {

    }

    @Override
    protected void deactivateMode() {

    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {

    }

}
