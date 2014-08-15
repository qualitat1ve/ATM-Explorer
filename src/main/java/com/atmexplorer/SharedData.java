package com.atmexplorer;

import com.atmexplorer.model.ATMItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for share data between modes.
 */
public class SharedData {

    public static final String NAVIGATION_TYPE_PREFERENCE = "navi_type";
    public static final String LIMIT_DISTANCE = "pref_distance_key";

    private List<ATMItem> mItems = new ArrayList<ATMItem>();
    private ATMItem mCurrentItem = null;

    public void addItem(ATMItem item) {
        mItems.add(item);
    }

    public void removeItem(ATMItem item){
        mItems.remove(item);
    }

    public boolean contains(ATMItem item) {
        return mItems.contains(item);
    }

    public void clearAll() {
        mItems.clear();
    }

    public void setItemList(List<ATMItem> items) {
        mItems = items;
    }

    public void setCurrentItem(ATMItem item) {
        mCurrentItem = item;
    }

    public final ATMItem getCurrentItem() {
        return mCurrentItem;
    }

    public final List<ATMItem> getItems (){
        return mItems;
    }
}
