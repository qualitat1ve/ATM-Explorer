package com.atmexplorer.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Provide some support operations
 */
public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();
    private Utils() {

    }

    /**
     * Round to certain number of decimals
     * @param d - target value
     * @param decimalPlace - number of decimal places
     * @return
     */
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Allow to copy database
     */
    public static void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.atmexplorer" +"/databases/atm_db";
        String backupDBPath = "atm_db_backup";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch(IOException e) {
            Log.e(LOG_TAG, "Export database can't be finished successfully", e);
        }
    }
}
