package com.atmexplorer.model;

import android.app.Fragment;

/**
 * Created by m.kukushkin on 20.05.2014.
 */
public class SpinnerNavigationItem {
    private String mItemTitle;
    private int mIconId;
    private Fragment mFragment;

    public SpinnerNavigationItem(String itemTitle, int iconId, Fragment fragment) {
        mItemTitle = itemTitle;
        mIconId = iconId;
        mFragment = fragment;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public int getIconId() {
        return mIconId;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
