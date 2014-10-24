package com.atmexplorer.database;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import com.atmexplorer.SharedData;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.model.DataProvider;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static final String KEY_CITY_NAME = "city_name";
    public static final String KEY_CITY_NAME_UA = "city_name_ua";
    public static final String KEY_CITY_NAME_EN = "city_name_en";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ADDRESS_UA = "address_ua";
    public static final String KEY_ADDRESS_EN = "address_en";
    public static final String KEY_BANK_NAME = "bank_name";
    public static final String KEY_BANK_NAME_UA = "bank_name_ua";
    public static final String KEY_BANK_NAME_EN = "bank_name_en";
    public static final String KEY_BANK_LOGO = "logo_name";
    public static final String KEY_OPERATION_TIME = "time";
    public static final String KEY_OPERATION_TIME_UA = "time_ua";
    public static final String KEY_OPERATION_TIME_EN = "time_en";
    public static final String KEY_POSITION = "position";
    public static final String KEY_POSITION_UA = "position_ua";
    public static final String KEY_POSITION_EN = "position_en";
    public static final String KEY_DESCRIPTION = "type";
    public static final String KEY_DESCRIPTION_UA = "type_ua";
    public static final String KEY_DESCRIPTION_EN = "type_en";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_BANK_GROUP_NAME = "group_name";
    private static final String ATM_TABLE_NAME = "atms";
    private static final String CITY_TABLE_NAME = "cities";
    private static final String BANK_TABLE_NAME = "banks";
    private static final String BANK_GROUP_TABLE_NAME = "bank_groups";
    private static final String SELECT_ALL_QUERY = "SELECT atms._id," +
            " banks.bank_name, banks.bank_name_ua, banks.bank_name_en," +
            " cities.city_name, cities.city_name_ua, cities.city_name_en," +
            " atms.address, atms.address_ua, atms.address_en," +
            " atms.time, atms.time_ua, atms.time_en," +
            " atms.position, atms.position_ua, atms.position_en," +
            " atms.type, atms.type_ua, atms.type_en, " +
            " atms.latitude, atms.longitude, banks.logo_name FROM atms" +
            " INNER JOIN banks ON banks._id=atms.id_bank" +
            " INNER JOIN cities ON cities._id=atms.id_city" +
            " INNER JOIN bank_groups ON bank_groups._id=banks.id_bank_group";
    private static final String FILTER_BY_ADDRESS_QUERY = SELECT_ALL_QUERY + " WHERE atms.address LIKE ?";
    private static final String SELECT_BANKS_FROM_GROUP_QUERY =  SELECT_ALL_QUERY + " WHERE bank_groups._id LIKE ?";

    private CashMachineDao mCashMachineDao;
    private BankDao mBankDao;
    private CityDao mCityDao;
    private List<ATMItem> mATMList = new ArrayList<ATMItem>();

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    private static final HashMap<String,String> mColumnMap = buildColumnMap();

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
        Cursor cursor = mDb.rawQuery(SELECT_ALL_QUERY, null);
        mATMList = prepareDataToShow(cursor);
        return mATMList;
    }

    public List<ATMItem> getBanksFromGroup(int groupId){
        Cursor cursor = mDb.rawQuery(SELECT_BANKS_FROM_GROUP_QUERY, new String[]{String.valueOf(groupId)});
        return prepareDataToShow(cursor);
    }

    public void close() {
        mDbHelper.close();
    }

    @Override
    public List<Persistent> getListData() {
        throw new RuntimeException("Unsupported operation");
    }

    public final Cursor getAddress(String rowId, String[] columns) {
        String selection = "rowId = ?";
        String[] selectionArgs = new String[]{rowId};
        return query(selection, selectionArgs, columns);
    }

    public final Cursor getWordMatches(String query, String[] columns) {
        String selection = KEY_ADDRESS + " LIKE ?";
        String[] selectionArgs = new String[] {"%" + query + "%"};
        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(ATM_TABLE_NAME);
        builder.setProjectionMap(mColumnMap);

        final Cursor cursor = builder.query(mDbHelper.getReadableDatabase(), columns, selection, selectionArgs, null, null, null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private List<ATMItem> prepareDataToShow(Cursor cursor) {
        mATMList.clear();
        String language = PreferenceManager.getDefaultSharedPreferences(mContext).getString(SharedData.LANGUAGE, null);
        if (cursor.moveToFirst()) {
            do {
                int atmId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                double latitude = cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE));
                String bankLogo = cursor.getString(cursor.getColumnIndex(KEY_BANK_LOGO));
                String bankName;
                String cityName;
                String address;
                String workingTime;
                String atmPosition;
                String description;
                int logoId = mContext.getResources().getIdentifier(bankLogo, "drawable", mContext.getPackageName());
                if ("ua".equals(language)) {
                    bankName = cursor.getString(cursor.getColumnIndex(KEY_BANK_NAME_UA));
                    cityName = cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME_UA));
                    address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS_UA));
                    workingTime = cursor.getString(cursor.getColumnIndex(KEY_OPERATION_TIME_UA));
                    atmPosition = cursor.getString(cursor.getColumnIndex(KEY_POSITION_UA));
                    description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION_UA));
                } else if ("en".equals(language)) {
                    bankName = cursor.getString(cursor.getColumnIndex(KEY_BANK_NAME_EN));
                    cityName = cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME_EN));
                    address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS_EN));
                    workingTime = cursor.getString(cursor.getColumnIndex(KEY_OPERATION_TIME_EN));
                    atmPosition = cursor.getString(cursor.getColumnIndex(KEY_POSITION_EN));
                    description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION_EN));
                } else {
                    bankName = cursor.getString(cursor.getColumnIndex(KEY_BANK_NAME));
                    cityName = cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME));
                    address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                    workingTime = cursor.getString(cursor.getColumnIndex(KEY_OPERATION_TIME));
                    atmPosition = cursor.getString(cursor.getColumnIndex(KEY_POSITION));
                    description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
                }

                ATMItem item = new ATMItem(atmId, cityName, address, bankName, logoId, latitude, longitude, workingTime,
                        atmPosition, description);
                mATMList.add(item);
            } while (cursor.moveToNext());
        }
        return mATMList;
    }

    private static HashMap<String, String> buildColumnMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ADDRESS, KEY_ADDRESS + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
        return map;
    }
}

