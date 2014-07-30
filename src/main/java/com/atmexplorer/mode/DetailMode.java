package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.atmexplorer.R;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Mode for detail info about item
 */
public class DetailMode extends Fragment implements Mode {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onChangeState(ActiveState state) {
    }

    @Override
    public Fragment getModeFragment() {
        return this;
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {
    }
}
