package com.atmexplorer.model.entity;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Common interface to generalize all entity stored to DataBase
 */
public interface Persistent {
    int getId();
    void setId(int id);
}
