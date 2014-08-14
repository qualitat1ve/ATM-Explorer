package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import com.atmexplorer.SharedData;
import com.atmexplorer.utils.Should;


/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Base fragment for any mode
 */
public abstract class BaseMode <T extends Fragment> implements Mode {

    protected T fragment;
    protected SharedData mSharedData = null;

    protected BaseMode(final SharedData sharedData, T fragment) {
        Should.beNotNull(sharedData, "SharedData must be not null!");
        Should.beNotNull(fragment, "Mode's fragment wasn't initialized properly, fragment is: " + fragment);
        mSharedData = sharedData;
        this.fragment = fragment;
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

    public final T getModeFragment() {
        return fragment;
    }

    protected abstract void setupMode();

    protected abstract void deactivateMode();
}
