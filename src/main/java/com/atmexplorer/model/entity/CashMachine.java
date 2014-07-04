package com.atmexplorer.model.entity;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief ATM bean
 */
public class CashMachine implements Persistent{
    private int mId;
    private int mBankId;
    private int mCityId;
    private String mAddress;
    private String mMode;
    private String mLocation;

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    public int getBankId() {
        return mBankId;
    }

    public void setBankId(int bankId) {
        this.mBankId = bankId;
    }

    public int getIdCity() {
        return mCityId;
    }

    public void setIdCity(int cityId) {
        mCityId = cityId;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getMode() {
        return mMode;
    }

    public void setMode(String mode) {
        mMode = mode;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
}
