package com.atmexplorer.model;

/**
 * @author m.kukushkin (maks.kukushkin@gmail.com)
 * @deprecated
 */
public class ATMItem {
    private String mDescription;
    private String mAddress;
    private String mWorkingTime;
    private String mBankName;
    private int mDistance;
    private int mIconId;

    public ATMItem(String address, String bankName, int iconId) {
        this.mAddress = address;
        this.mBankName = bankName;
        this.mIconId = iconId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getWorkingTime() {
        return mWorkingTime;
    }

    public void setWorkingTime(String workingTime) {
        mWorkingTime = workingTime;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }
}
