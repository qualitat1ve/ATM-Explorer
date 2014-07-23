package com.atmexplorer.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.atmexplorer.database.DataBaseHelper;
import com.atmexplorer.model.entity.CashMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief
 */
public class CashMachineDaoImpl extends BaseDao<CashMachine> implements CashMachineDao {

    public CashMachineDaoImpl(DataBaseHelper dataBaseHelper, String tableName) {
        super(dataBaseHelper, tableName);
    }

    @Override
    protected ContentValues beforeCreate(CashMachine persistent) {
        return null;
    }

    @Override
    protected ContentValues beforeUpdate(CashMachine persistent) {
        return null;
    }

    @Override
    protected CashMachine afterReadingSingle(Cursor cursor) {
        return null;
    }

    @Override
    protected List<CashMachine> afterReadingMultiple(Cursor cursor) {
        List<CashMachine> list = new ArrayList<CashMachine>();
        return list;
    }
}
