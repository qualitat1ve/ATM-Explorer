package com.atmexplorer.database;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Oleksandr Stetsko (alexandr.stetsko@gmail.com)
 * @brief DataBase Adapter
 */
public class DataBaseAdapter {
    protected static final String TAG = "DataAdapter";

    public static final String KEY_ADDRESS_REGION = "region";
    public static final String KEY_ADDRESS_CITY = "city";
    public static final String KEY_ADDRESS_STREET = "street";
    public static final String KEY_BANK_NAME = "name";
    public static final String KEY_OPERATION_TIME = "mode";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TYPE = "type";
    private static final String TABLE_NAME = "atms";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataBaseAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataBaseAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataBaseAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public Cursor getAllData() {
        return mDb.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor filterByString(String constraint) {
        String []selection = new String [] {"%" + constraint + "%", "%" + constraint + "%"};
        return mDb.rawQuery("SELECT * FROM atms WHERE street like ? COLLATE NOCASE OR city like ? COLLATE NOCASE", selection);
    }

    public void close() {
        mDbHelper.close();
    }
}

