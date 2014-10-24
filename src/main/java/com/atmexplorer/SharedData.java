package com.atmexplorer;

import com.atmexplorer.model.ATMItem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for share data between modes.
 */
public class SharedData {

    private static SharedData instance = null;

    private SharedData() {
        instance = this;
    }

    public static synchronized SharedData getInstance() {
        return (instance == null) ? new SharedData() : instance;
    }

    public static final String NAVIGATION_TYPE_PREFERENCE = "navi_type";
    public static final String LIMIT_DISTANCE = "pref_distance_key";
    public static final String LANGUAGE = "language_type";

    private Set<ATMItem> mItems = new HashSet<ATMItem>();
    private ATMItem mCurrentItem = null;

    public void addItems(final Collection<ATMItem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

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

    public void setCurrentItem(ATMItem item) {
        mCurrentItem = item;
    }

    public final ATMItem getCurrentItem() {
        return mCurrentItem;
    }

    public final Set<ATMItem> getItems (){
        return mItems;
    }
}
