package com.atmexplorer.model.entity;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Bank bean
 */
public class Bank implements Persistent {
    private int mId;
    private String mName;
    private String mLogoName;

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLogoName() {
        return mLogoName;
    }

    public void setLogoName(String logoName) {
        mLogoName = logoName;
    }
}
