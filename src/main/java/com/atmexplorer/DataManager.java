package com.atmexplorer;

import com.atmexplorer.model.ATMItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Use for share data between modes.
 */
public class DataManager {

    private List<ATMItem> mItems = new ArrayList<ATMItem>();

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

    public final List<ATMItem> getItems (){
        return mItems;
    }
}
