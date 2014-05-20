package com.atmexplorer.model;

/**
 * Created by m.kukushkin on 20.05.2014.
 */
public class SpinnerNavigationItem {
    private String mItemTitle;
    private int mIconId;

    public SpinnerNavigationItem(String itemTitle, int iconId) {
        mItemTitle = itemTitle;
        mIconId = iconId;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public int getIconId() {
        return mIconId;
    }
}
