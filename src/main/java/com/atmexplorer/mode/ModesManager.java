package com.atmexplorer.mode;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.atmexplorer.Data;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.builder.DetailBuilder;
import com.atmexplorer.builder.ListModeBuilder;
import com.atmexplorer.builder.MapModeBuilder;
import com.atmexplorer.utils.Should;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Responsible for changing application states: LIST-MAP-DETAIL
 */
public class ModesManager {
    enum  ModeIndex{
        LIST, MAP, DETAIL;
        public int index() {
            return ordinal();
        }
    }

    private ActionBar mActionBar;
    private Activity mActivity;
    private Mode[] mModes =  new Mode[3];
    private int mDefaultModeIndex = ModeIndex.LIST.index();
    private Mode mActiveMode;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mDrawerMenu;
    private final List<Mode> mStack = new ArrayList<Mode>(mModes.length);

    public ModesManager(Activity activity) {
        Should.beNotNull(activity, "Main activity is null!");
        mActivity = activity;
        mActionBar = mActivity.getActionBar();
        Data dataManager = new Data();

        LocationTracker locationTracker =  new LocationTracker(activity.getApplicationContext());
        ModeChangeRequester modeChangeRequester = new ModeChangeRequester();

        View rootView  = activity.findViewById(R.id.fragment_container);

        ListModeBuilder listModeBuilder =  new ListModeBuilder(rootView, dataManager, locationTracker, modeChangeRequester);
        mModes[ModeIndex.LIST.index()] = listModeBuilder.build();

        MapModeBuilder mapModeBuilder =  new MapModeBuilder(rootView, dataManager, modeChangeRequester);
        mModes[ModeIndex.MAP.index()] = mapModeBuilder.build();
        mActiveMode = mModes[mDefaultModeIndex];

        DetailBuilder detailBuilder =  new DetailBuilder(rootView, dataManager, modeChangeRequester);
        mModes[ModeIndex.DETAIL.index()] = detailBuilder.build();

        activateDefaultMode();

        setUpDrawer();
    }

    private void setUpDrawer() {
        mDrawerMenu = mActivity.findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mDrawerToggle = new CustomDrawerToggle(mActivity, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open_title, R.string.drawer_close_title);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void activate(ModeIndex modeId) {
        activate(modeId, true);
    }

    public void activate(ModeIndex modeId, boolean isAddToBackStack) {
        switch (modeId) {
            case DETAIL:
                if (!mActionBar.isShowing()) {
                    mActionBar.show();
                }
                break;
            case LIST:
                if (!mActionBar.isShowing()) {
                    mActionBar.show();
                }
                break;
            case MAP:
                mActionBar.hide();
                break;
            default: throw new UnsupportedOperationException("Unknown mode: " + modeId);
        }
        if(isAddToBackStack) {
            addToBackStack(modeId);
        }
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mActiveMode.onChangeState(Mode.ActiveState.INACTIVE);
        mActiveMode = mModes[modeId.index()];
        mActiveMode.onChangeState(Mode.ActiveState.ACTIVE);
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, mModes[modeId.index()].getModeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    public void activateDefaultMode() {
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
        if(fragmentTransaction.isEmpty()) {
            Fragment targetFragment = mModes[mDefaultModeIndex].getModeFragment();
            fragmentTransaction.add(R.id.fragment_container, targetFragment);
        }else{
            //TODO
        }
        fragmentTransaction.commit();
        addToBackStack(ModeIndex.values()[mDefaultModeIndex]);
    }

    public Mode getActiveMode() {
        return  mActiveMode;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    public void onNewIntent(Intent intent) {
        mActiveMode.onNewIntent(intent);
    }

    public boolean onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            if (mStack.size() <= 1) {
                mStack.clear();
                return false;
            } else {
                mActiveMode.onBackPressed();
                mStack.remove(mActiveMode);
                activate(ModeIndex.values()[findModeIndex(mStack.get(mStack.size()-1))]);
            }
        }
        return true;
    }

    public void onPostCreate(Bundle savedInstanceState) {
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerMenu);
        //TODO: if search view is expanded = collapse it 1st
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
    }

    public final class ModeChangeRequester {
        /**
         *
         * @param index request index of mode for switch to front
         * @param isAddToBackStack determine opportunity add to backstack
         */
        public void onModeChange(ModeIndex index, boolean isAddToBackStack) {
            activate(index, isAddToBackStack);
        }
    }

    private int findModeIndex(Mode mode) {
        for(int i = 0; i < mModes.length; i++) {
            Mode m = mModes[i];
            if(m.equals(mode)) {
                return i;
            }
        }
        return -1;
    }

    private void addToBackStack(ModeIndex modeId) {
        if(!mStack.contains(mModes[modeId.index()])) {
            mStack.add(mModes[modeId.index()]);
        }
    }

    private class CustomDrawerToggle extends ActionBarDrawerToggle {

        public CustomDrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            mActionBar.setTitle(mActivity.getString(R.string.drawer_close_title));
            mActivity.invalidateOptionsMenu();
        }

        public void onDrawerOpened(View view) {
            super.onDrawerOpened(view);
            mActionBar.setTitle(mActivity.getString(R.string.drawer_open_title));
            mActivity.invalidateOptionsMenu();
        }
    }
}
