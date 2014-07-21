package com.atmexplorer.model;


import com.atmexplorer.model.entity.Persistent;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Interface for data exchange between the data source and UI
 */
public interface DataProvider<T extends Persistent> {
    //for example
    List<T> getListData();
}
