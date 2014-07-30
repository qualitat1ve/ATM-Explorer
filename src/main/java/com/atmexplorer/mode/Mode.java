package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Base interface for mode
 */
public interface Mode {
    enum ActiveState {
        ACTIVE,
        INACTIVE
    }

    void onChangeState(ActiveState state);
    void onNewIntent(Intent intent);
    void onBackPressed();
    abstract Fragment getModeFragment();
}
