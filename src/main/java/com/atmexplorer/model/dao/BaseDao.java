package com.atmexplorer.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.atmexplorer.database.DataBaseHelper;
import com.atmexplorer.model.entity.Persistent;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Base DAO (CRUD) implementation
 */
public abstract class BaseDao<T extends Persistent> implements Dao<T> {

    private DataBaseHelper mDataBaseHelper;
    private String mTableName;

    protected BaseDao(DataBaseHelper dataBaseHelper, String tableName) {
        mDataBaseHelper = dataBaseHelper;
        mTableName = tableName;
    }

    protected DataBaseHelper getDataBaseHelper() {
        return mDataBaseHelper;
    }

    protected String getTableName() {
        return mTableName;
    }

    @Override
    public long create(T persistent) {
        SQLiteDatabase dataBase = mDataBaseHelper.getWritableDatabase();
        long objectId = dataBase.insert(mTableName, null, beforeCreate(persistent));
        return objectId;
    }

    @Override
    public List<T> readAll() {
        SQLiteDatabase dataBase = mDataBaseHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mTableName;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        return afterReadingMultiple(cursor);
    }

    @Override
    public T read(int objectId) {
        SQLiteDatabase dataBase = mDataBaseHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + mTableName + " WHERE _id = " + objectId;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        return afterReadingSingle(cursor);
    }

    @Override
    public int update(T persistent) {
        SQLiteDatabase dataBase = mDataBaseHelper.getWritableDatabase();
        int updateCount = dataBase.update(mTableName, beforeUpdate(persistent), "_id = ?",
                new String[] {String.valueOf(persistent.getId())});
        return updateCount;
    }

    @Override
    public int delete(T persistent) {
        SQLiteDatabase dataBase = mDataBaseHelper.getWritableDatabase();
        int deleteCount = dataBase.delete(mTableName, "_id = " + persistent.getId(), null);
        return deleteCount;
    }

    protected abstract ContentValues beforeCreate(T persistent);
    protected abstract ContentValues beforeUpdate(T persistent);
    protected abstract T afterReadingSingle(Cursor cursor);
    protected abstract List<T> afterReadingMultiple(Cursor cursor);
}
