package com.atmexplorer.database;

import com.atmexplorer.model.DataProvider;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.atmexplorer.model.dao.BankDao;
import com.atmexplorer.model.dao.BankDaoImpl;
import com.atmexplorer.model.dao.CashMachineDao;
import com.atmexplorer.model.dao.CashMachineDaoImpl;
import com.atmexplorer.model.dao.CityDao;
import com.atmexplorer.model.dao.CityDaoImpl;

/**
 * @author Oleksandr Stetsko (alexandr.stetsko@gmail.com)
 * @brief DataBase Adapter
 */
public class DataBaseAdapter implements DataProvider {
    protected static final String TAG = "DataAdapter";

    public static final String KEY_ADDRESS_REGION = "region";
    public static final String KEY_ADDRESS_CITY = "city";
    public static final String KEY_ADDRESS_STREET = "street";
    public static final String KEY_BANK_NAME = "name";
    public static final String KEY_OPERATION_TIME = "mode";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TYPE = "type";
    private static final String ATM_TABLE_NAME = "atms";
    private static final String CITY_TABLE_NAME = "cities";
    private static final String BANK_TABLE_NAME = "banks";
    private CashMachineDao mCashMachineDao;
    private BankDao mBankDao;
    private CityDao mCityDao;


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
        //TODO: refactor: are you sure you want to open database just to close it in next line?
        mDbHelper.openDataBase();
        mDbHelper.close();
        mDb = mDbHelper.getReadableDatabase();
    }

    public Cursor getAllData() {
        return mDb.query(ATM_TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor filterByString(String constraint) {
        String []selection = new String [] {"%" + constraint + "%", "%" + constraint + "%"};
        return mDb.rawQuery("SELECT * FROM atms WHERE street like ? OR city like ?", selection);
    }

    public void close() {
        mDbHelper.close();
    }

    @Override
    public List<Object> getListData() {
        //TODO: remove stub
        return null;
    }
}

