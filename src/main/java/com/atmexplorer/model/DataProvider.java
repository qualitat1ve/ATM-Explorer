package com.atmexplorer.model;


import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Interface for data exchange between the data source and UI
 */
public interface DataProvider {
    //for example
    List<Object> getListData();
}
