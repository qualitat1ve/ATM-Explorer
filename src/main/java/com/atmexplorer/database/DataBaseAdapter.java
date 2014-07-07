package com.atmexplorer.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void createDatabase() {
        mDbHelper.createDataBase();
    }

    public void open() {
        //TODO: refactor: are you sure you want to open database just to close it in next line?
        mDbHelper.openDataBase();
        mDbHelper.close();
        mDb = mDbHelper.getReadableDatabase();
    }

    public Cursor getAllData() {
        return mDb.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor filterByString(String constraint) {
        String []selection = new String [] {"%" + constraint + "%", "%" + constraint + "%"};
        return mDb.rawQuery("SELECT * FROM atms WHERE street like ? OR city like ?", selection);
    }

    public void close() {
        mDbHelper.close();
    }
}

