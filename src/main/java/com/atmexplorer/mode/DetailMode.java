package com.atmexplorer.mode;

import android.content.Intent;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.DetailModeFragment;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Mode for detail info about item
 */
public class DetailMode extends BaseMode {

    private DetailModeFragment mDetailFragment;

    public DetailMode(SharedData sharedData, DetailModeFragment fragment) {
        super(sharedData, fragment);
        mDetailFragment = fragment;
    }

    @Override
    protected void setupMode() {
        mDetailFragment.setupView();
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
