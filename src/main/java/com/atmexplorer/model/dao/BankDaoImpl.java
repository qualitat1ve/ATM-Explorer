package com.atmexplorer.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.atmexplorer.database.DataBaseHelper;
import com.atmexplorer.model.entity.Bank;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief
 */
public class BankDaoImpl extends BaseDao<Bank> implements BankDao {

    public BankDaoImpl(DataBaseHelper dataBaseHelper, String tableName) {
        super(dataBaseHelper, tableName);
    }

    @Override
    protected ContentValues beforeCreate(Bank persistent) {
        return null;
    }

    @Override
    protected ContentValues beforeUpdate(Bank persistent) {
        return null;
    }

    @Override
    protected Bank afterReadingSingle(Cursor cursor) {
        return null;
    }

    @Override
    protected List<Bank> afterReadingMultiple(Cursor cursor) {
        return null;
    }
}
