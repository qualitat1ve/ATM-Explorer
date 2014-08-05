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
    private String mPosition;
    private String mBankName;
    private int mDistance;
    private int mIconId;
    private int mId;
    private double mLatitude;
    private double mLongitude;

    public ATMItem() {

    }

    public ATMItem(int id, String city, String address, String bankName, int iconId, double latitude, double longitude,
                   String workingTime, String position, String description) {
        mId = id;
        mCity = city;
        mAddress = address;
        mBankName = bankName;
        mIconId = iconId;
        mLatitude = latitude;
        mLongitude = longitude;
        mWorkingTime = workingTime;
        mPosition = position;
        mDescription = description;
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

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        this.mPosition = position;
    }

    public Location getLocation() {
        // provider name isn't necessary
        Location location = new Location("");
        location.setLatitude(mLatitude);
        location.setLongitude(mLongitude);
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ATMItem)) return false;

        ATMItem atmItem = (ATMItem) o;

        if (mDistance != atmItem.mDistance) return false;
        if (mIconId != atmItem.mIconId) return false;
        if (mId != atmItem.mId) return false;
        if (Double.compare(atmItem.mLatitude, mLatitude) != 0) return false;
        if (Double.compare(atmItem.mLongitude, mLongitude) != 0) return false;
        if (!mAddress.equals(atmItem.mAddress)) return false;
        if (!mBankName.equals(atmItem.mBankName)) return false;
        if (!mCity.equals(atmItem.mCity)) return false;
        if (!mDescription.equals(atmItem.mDescription)) return false;
        if (!mPosition.equals(atmItem.mPosition)) return false;
        if (!mWorkingTime.equals(atmItem.mWorkingTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mDescription.hashCode();
        result = 31 * result + mAddress.hashCode();
        result = 31 * result + mCity.hashCode();
        result = 31 * result + mWorkingTime.hashCode();
        result = 31 * result + mPosition.hashCode();
        result = 31 * result + mBankName.hashCode();
        result = 31 * result + mDistance;
        result = 31 * result + mIconId;
        result = 31 * result + mId;
        temp = Double.doubleToLongBits(mLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ATMItem {" +
                "Longitude = " + mLongitude +
                ", Latitude = " + mLatitude +
                ", City = '" + mCity + '\'' +
                ", Address = '" + mAddress + '\'' +
                ", BankName = '" + mBankName + '\'' +
                ", Description = '" + mDescription + '\'' +
                ", WorkingTime = '" + mWorkingTime + '\'' +
                ", Position = '" + mPosition + '\'' +
                ", Id = " + mId +
                '}';
    }
}