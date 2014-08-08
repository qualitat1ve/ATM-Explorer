package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.atmexplorer.Data;
import com.atmexplorer.utils.Should;


/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Base fragment for any mode
 */
public abstract class BaseMode extends Fragment implements Mode {

    protected Data mData = null;
    protected BaseMode(final Data data) {
        Should.beNotNull(data, "Data must be not null!");
        mData = data;
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

    public final Fragment getModeFragment() {
        return this;
    }

    protected abstract void setupMode();

    protected abstract void deactivateMode();
}
