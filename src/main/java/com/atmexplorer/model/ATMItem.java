package com.atmexplorer.model;

import android.location.Location;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief bean class to keep up ListFragment's elements
 */
public class ATMItem {

    private String mDescription;
    private String mAddress;
    private String mCity;
    private String mWorkingTime;
    private String mBankName;
    private int mDistance;
    private int mIconId;
    private int mId;
    private double mLatitude;
    private double mLongitude;

    public ATMItem() {}

    public ATMItem(int id, String city, String address, String bankName, int iconId, double latitude, double longitude) {
        mId = id;
        mCity = city;
        mAddress = address;
        mBankName = bankName;
        mIconId = iconId;
        mLatitude = latitude;
        mLongitude = longitude;
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

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getFullAddress() {
        return mCity + ", " + mAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Location getLocation() {
        // provider name isn't necessary
        Location location = new Location("");
        location.setLatitude(mLatitude);
        location.setLongitude(mLongitude);
        return location;
    }
}