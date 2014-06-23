package com.atmexplorer.model;

import android.app.Fragment;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief bean for ActionBar navigation item
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
