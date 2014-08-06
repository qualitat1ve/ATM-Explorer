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

    protected ActionBarDrawerToggle drawerToggle;

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        drawerToggle = ((AtmExplorer)getActivity()).getDrawerToggle();
    }
    @Override
    public void onChangeState(ActiveState state) {
        boolean showHomeButton;
        switch (state) {
            case ACTIVE:
                showHomeButton = true;
                setupMode();
                break;
            case INACTIVE:
                showHomeButton = false;
                deactivateMode();
                break;
            default:
                throw new UnsupportedOperationException("Unknown state: " + state);
        }
        drawerToggle.setDrawerIndicatorEnabled(!showHomeButton);
    }

    public abstract void onNewIntent(Intent intent);

    public abstract void onBackPressed();

    public abstract Fragment getModeFragment();

    protected abstract void setupMode();

    protected abstract void deactivateMode();
}
