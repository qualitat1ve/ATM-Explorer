package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import com.atmexplorer.AtmExplorer;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Base fragment for any mode
 */
public abstract class BaseMode extends Fragment implements Mode {

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }
    @Override
    public void onChangeState(ActiveState state) {
        switch (state) {
            case ACTIVE:
                setupMode();
                break;
            case INACTIVE:
                deactivateMode();
                break;
            default:
                throw new UnsupportedOperationException("Unknown state: " + state);
        }

    }

    public abstract void onNewIntent(Intent intent);

    public abstract void onBackPressed();

    public abstract Fragment getModeFragment();

    protected abstract void setupMode();

    protected abstract void deactivateMode();
}
