package com.atmexplorer.model.entity;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief City bean
 */
public class City implements Persistent {
    private int mId;
    private String mName;

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
}
