package com.atmexplorer.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Oleksandr Stetsko (alexandr.stetsko@gmail.com)
 * @brief DataBase helper for work wih db
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper";
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME = "atm_db";
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        mContext = context;
    }

    public void createDataBase() {
        //If database not exists copy it from the assets
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() {
        byte[] mBuffer = new byte[1024];
        int mLength;
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            try {
                while ((mLength = mInput.read(mBuffer)) > 0) {
                    mOutput.write(mBuffer, 0, mLength);
                }
            } finally {
                mOutput.flush();
                mOutput.close();
                mInput.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to copy DataBase from assets", e);
        }
    }

    //Open the database, so we can query it
    public boolean openDataBase() {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // unused callback
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // unused callback
    }

}
