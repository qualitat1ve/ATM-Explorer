package com.atmexplorer.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.atmexplorer.database.DataBaseHelper;
import com.atmexplorer.model.entity.City;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief
 */
public class CityDaoImpl extends BaseDao<City> implements CityDao {

    public CityDaoImpl(DataBaseHelper dataBaseHelper, String tableName) {
        super(dataBaseHelper, tableName);
    }

    @Override
    protected ContentValues beforeCreate(City persistent) {
        return null;
    }

    @Override
    protected ContentValues beforeUpdate(City persistent) {
        return null;
    }

    @Override
    protected City afterReadingSingle(Cursor cursor) {
        return null;
    }

    @Override
    protected List<City> afterReadingMultiple(Cursor cursor) {
        return null;
    }
}
