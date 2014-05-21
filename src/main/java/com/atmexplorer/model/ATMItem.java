package com.atmexplorer.model;

/**
 * Created by m.kukushkin on 21.05.2014.
 */
public class ATMItem {
    private String mDescription;
    private String mAddress;
    private String mWorkingTime;
    private String mBankName;
    private int mIconId;

    public String getDescription() {
        return mDescription;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getWorkingTime() {
        return mWorkingTime;
    }

    public String getBankName() {
        return mBankName;
    }

    public int getIconId() {
        return mIconId;
    }
}
