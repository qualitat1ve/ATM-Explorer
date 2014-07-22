package com.atmexplorer.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.atmexplorer.R;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.model.DataProvider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.atmexplorer.model.dao.BankDao;
import com.atmexplorer.model.dao.BankDaoImpl;
import com.atmexplorer.model.dao.CashMachineDao;
import com.atmexplorer.model.dao.CashMachineDaoImpl;
import com.atmexplorer.model.dao.CityDao;
import com.atmexplorer.model.dao.CityDaoImpl;
import com.atmexplorer.model.entity.Persistent;

/**
 * @author Oleksandr Stetsko (alexandr.stetsko@gmail.com)
 * @brief DataBase Adapter
 */
public class DataBaseAdapter implements DataProvider {
    protected static final String TAG = "DataAdapter";

    public static final String KEY_ID = "_id";
    public static final String KEY_CITY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BANK_NAME = "bank_name";
    public static final String KEY_BANK_LOGO = "logo";
    public static final String KEY_OPERATION_TIME = "time";
    public static final String KEY_POSITION = "position";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_COMMENT = "comment";
    private static final String ATM_TABLE_NAME = "Atm";
    private static final String CITY_TABLE_NAME = "City";
    private static final String BANK_TABLE_NAME = "Bank";
    private CashMachineDao mCashMachineDao;
    private BankDao mBankDao;
    private CityDao mCityDao;
    private List<ATMItem> mATMList;

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataBaseAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
        mCashMachineDao = new CashMachineDaoImpl(mDbHelper, ATM_TABLE_NAME);
        mBankDao = new BankDaoImpl(mDbHelper, BANK_TABLE_NAME);
        mCityDao = new CityDaoImpl(mDbHelper, CITY_TABLE_NAME);
    }

    public void createDatabase() {
        mDbHelper.createDataBase();
    }

    public void open() {
        mDbHelper.openDataBase();
        mDb = mDbHelper.getWritableDatabase();
    }

    public List<ATMItem> getAllData() {
        return prepareDataToShow();
    }

    public void close() {
        mDbHelper.close();
    }

    @Override
    public List<Persistent> getListData() {
        //TODO: remove stub
        return null;
    }

    /**
     * Method updates ATM's coordinates according to their address.
     * Should be used once after installation of application.
     * @param item ATMItem
     */
    public void updateCoordinates(ATMItem item) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, item.getLatitude());
        values.put(KEY_LONGITUDE, item.getLongitude());
        mDb.update(ATM_TABLE_NAME, values, "_id = ?", new String[] {String.valueOf(item.getId())});
        close();
    }

    private List<ATMItem> prepareDataToShow() {
        mATMList = new ArrayList<ATMItem>();
        Cursor cursor = mDb.rawQuery(mContext.getResources().getString(R.string.sql_select_all), null);
        if (cursor.moveToFirst()) {
            do {
                int atmId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String bankName = cursor.getString(cursor.getColumnIndex(KEY_BANK_NAME));
                String cityName = cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME));
                String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                String bankLogo = cursor.getString(cursor.getColumnIndex(KEY_BANK_LOGO));
                int logoId = mContext.getResources().getIdentifier(bankLogo, "drawable", mContext.getPackageName());
                ATMItem item = new ATMItem(atmId, cityName, address, bankName, logoId);
                mATMList.add(item);
            } while (cursor.moveToNext());
        }
        return mATMList;
    }
}

