package com.atmexplorer.model.dao;

import com.atmexplorer.model.entity.Persistent;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Base data access object interface
 */
public interface Dao<T extends Persistent> {
    long create(T persistent);
    List<T> readAll();
    T read(int objectId);
    int update(T persistent);
    int delete(T persistent);
}
