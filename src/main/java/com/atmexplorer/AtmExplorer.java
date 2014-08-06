package com.atmexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import com.atmexplorer.mode.ListMode;
import com.atmexplorer.mode.ModesManager;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 */
public class AtmExplorer extends Activity {

    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mDrawerMenu;
    private ModesManager mModesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        setUpDrawer();
        mModesManager = new ModesManager(this);
    }


    private void setUpDrawer() {
        mDrawerMenu = findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new CustomDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open_title, R.string.drawer_close_title);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mModesManager.onBackPressed();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerMenu);
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return mModesManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    protected void onNewIntent(Intent intent) {
        mModesManager.onNewIntent(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
        if (!mActionBar.isShowing()) {
            mActionBar.show();
        }
        if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mModesManager.onBackPressed();
            super.onBackPressed();
        }
    }

    public final ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    private class CustomDrawerToggle extends ActionBarDrawerToggle {

        public CustomDrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            mActionBar.setTitle(getString(R.string.drawer_close_title));
            invalidateOptionsMenu();
        }

        public void onDrawerOpened(View view) {
            super.onDrawerOpened(view);
            mActionBar.setTitle(getString(R.string.drawer_open_title));
            invalidateOptionsMenu();
        }
    }
}
