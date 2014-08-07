package com.atmexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 */
public class AtmExplorer extends Activity {

    private ActionBar mActionBar;
    private ModesManager mModesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mModesManager = new ModesManager(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        mModesManager.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return mModesManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    protected void onNewIntent(Intent intent) {
        mModesManager.onNewIntent(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mModesManager.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mModesManager.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
        if (!mActionBar.isShowing()) {
            mActionBar.show();
        }
        if(!mModesManager.onBackPressed()) {
            finish();
        }
    }
}
