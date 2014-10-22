package com.atmexplorer.mode;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.SharedData;
import com.atmexplorer.builder.DetailBuilder;
import com.atmexplorer.builder.MainModeBuilder;
import com.atmexplorer.builder.MapModeBuilder;
import com.atmexplorer.builder.PreferencesModeBuilder;
import com.atmexplorer.content.MainModeFragment;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.Should;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Responsible for changing application states: LIST-MAP-DETAIL
 */
public class ModesManager {

    private static final String PREF_NAME_FILE = "atm_explorer_prefs";
    //Prefs values
    private static final String PREF_CURRENT_BANK_GROUP = "current_bankgroup";

    public enum  ModeIndex{
        LIST, MAP, DETAIL, SETTINGS;
        public int index() {
            return ordinal();
        }
    }

    public enum  BankGroupId{
        NONE, ATMOSPHERE, UKRCARD, ALL;
        public int index() {
            return ordinal();
        }
    }

    private ActionBar mActionBar;
    private Activity mActivity;
    private Mode[] mModes =  new Mode[4];
    private int mDefaultModeIndex = ModeIndex.LIST.index();
    private Mode mActiveMode;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mDrawerMenu;
    private final List<Mode> mStack = new ArrayList<Mode>(mModes.length);
    private DataBaseAdapter mDataBaseAdapter;

    private SharedPreferences mPrefs;

    private int mCurrentTitleRes = R.string.title_group_atmosphere;
    private int mCurrentBankGroupIndex;


    public ModesManager(Activity activity) {
        Should.beNotNull(activity, "Main activity is null!");
        mActivity = activity;
        mActionBar = mActivity.getActionBar();

        mPrefs = activity.getSharedPreferences(PREF_NAME_FILE, Activity.MODE_PRIVATE);
        readPrefs();
        SharedData sharedDataManager = new SharedData();

        mDataBaseAdapter = new DataBaseAdapter(activity);
        mDataBaseAdapter.createDatabase();
        mDataBaseAdapter.open();

        LocationTracker locationTracker =  new LocationTracker(activity.getApplicationContext());
        ModeChangeRequester modeChangeRequester = new ModeChangeRequester();

        View rootView  = activity.findViewById(R.id.fragment_container);

        MainModeBuilder mainModeBuilder =  new MainModeBuilder(rootView, sharedDataManager, locationTracker, modeChangeRequester, mDataBaseAdapter, mCurrentBankGroupIndex);
        mModes[ModeIndex.LIST.index()] = mainModeBuilder.build();

        MapModeBuilder mapModeBuilder =  new MapModeBuilder(rootView, sharedDataManager, locationTracker, modeChangeRequester);
        mModes[ModeIndex.MAP.index()] = mapModeBuilder.build();
        mActiveMode = mModes[mDefaultModeIndex];

        DetailBuilder detailBuilder =  new DetailBuilder(rootView, sharedDataManager, modeChangeRequester);
        mModes[ModeIndex.DETAIL.index()] = detailBuilder.build();

        PreferencesModeBuilder preferencesModeBuilder = new PreferencesModeBuilder(rootView, sharedDataManager, modeChangeRequester);
        mModes[ModeIndex.SETTINGS.index()] = preferencesModeBuilder.build();

        activateDefaultMode();

        setUpDrawer();
    }



    private void setUpDrawer() {
        mDrawerMenu = mActivity.findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mDrawerToggle = new CustomDrawerToggle(mActivity, mDrawerLayout, R.drawable.ic_drawer,
                R.string.title_bankgroup, R.string.title_group_atmosphere);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Button buttonShow = (Button)mDrawerLayout.findViewById(R.id.show_on_map);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                activate(ModeIndex.MAP);
            }
        });

        final Switch switcherAtm = (Switch)mDrawerLayout.findViewById(R.id.filter_atm);
        final Switch switcherUkrCard = (Switch)mDrawerLayout.findViewById(R.id.filter_ukrcard);

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    if(switcherAtm.equals(compoundButton)) {
                        mCurrentBankGroupIndex = BankGroupId.ATMOSPHERE.index();
                        switcherUkrCard.setChecked(!b);
                        mCurrentTitleRes = R.string.title_group_atmosphere;
                    }else if(switcherUkrCard.equals(compoundButton)) {
                        mCurrentBankGroupIndex = BankGroupId.UKRCARD.index();
                        switcherAtm.setChecked(!b);
                        mCurrentTitleRes = R.string.title_group_ukrcard;
                    }
                }else {
                    if(switcherAtm.equals(compoundButton)) {
                        mCurrentBankGroupIndex = BankGroupId.UKRCARD.index();
                        switcherUkrCard.setChecked(!b);
                    }else if(switcherUkrCard.equals(compoundButton)) {
                        mCurrentBankGroupIndex = BankGroupId.ATMOSPHERE.index();
                        switcherAtm.setChecked(!b);
                    }
                }
            }
        };
        switcherAtm.setOnCheckedChangeListener(onCheckedChangeListener);
        switcherUkrCard.setOnCheckedChangeListener(onCheckedChangeListener);
        initDrawerValues();
        if(mCurrentBankGroupIndex == BankGroupId.ATMOSPHERE.index()){
            mCurrentTitleRes = R.string.title_group_atmosphere;
        }else if(mCurrentBankGroupIndex == BankGroupId.UKRCARD.index()) {
            mCurrentTitleRes = R.string.title_group_ukrcard;
        }
        mActionBar.setTitle(mCurrentTitleRes);
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
            case SETTINGS:
                break;

            default: throw new UnsupportedOperationException("Unknown mode: " + modeId);
        }
        if(isAddToBackStack) {
            addToBackStack(modeId);
        }
        mActiveMode.onChangeState(Mode.ActiveState.INACTIVE);
        mActiveMode = mModes[modeId.index()];
        mActiveMode.onChangeState(Mode.ActiveState.ACTIVE);
        if(mActionBar.isShowing()){
            mActivity.invalidateOptionsMenu();
        }
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, mModes[modeId.index()].getModeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //Set state for toggleButton of drawer.
        mDrawerToggle.setDrawerIndicatorEnabled(mActiveMode.equals(mModes[mDefaultModeIndex]));
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                activate(ModeIndex.SETTINGS);
                break;

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
        MenuItem item = menu.findItem(R.id.action_search);
        if(item!=null) {
            item.setVisible(!isDrawerOpen);
        }
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
            updateListData(BankGroupId.values()[mCurrentBankGroupIndex]);
            mActionBar.setTitle(mActivity.getString(mCurrentTitleRes));
            mActivity.invalidateOptionsMenu();
            savePrefs();
            initDrawerValues();
        }

        public void onDrawerOpened(View view) {
            super.onDrawerOpened(view);
            readPrefs();
            initDrawerValues();
            mActionBar.setTitle(mActivity.getString(R.string.title_bankgroup));
            mActivity.invalidateOptionsMenu();
        }
    }

    public void destroy(){
        mDataBaseAdapter.close();
    }

    private void initDrawerValues() {
        final Switch switcherAtm = (Switch)mDrawerLayout.findViewById(R.id.filter_atm);
        final Switch switcherUkrCard = (Switch)mDrawerLayout.findViewById(R.id.filter_ukrcard);
        if(mCurrentBankGroupIndex == BankGroupId.ATMOSPHERE.index()) {
            switcherAtm.setChecked(true);
        }else if(mCurrentBankGroupIndex == BankGroupId.UKRCARD.index()){
            switcherUkrCard.setChecked(true);
        }
    }

    private void updateListData(BankGroupId bankId) {
        List<ATMItem> list = mDataBaseAdapter.getBanksFromGroup(bankId.index());
        ((MainModeFragment)mActiveMode.getModeFragment()).updateData(list);
    }

    private void savePrefs() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(PREF_CURRENT_BANK_GROUP, mCurrentBankGroupIndex);
        editor.commit();
    }

    private void readPrefs() {
        mCurrentBankGroupIndex = mPrefs.getInt(PREF_CURRENT_BANK_GROUP, BankGroupId.ATMOSPHERE.index());
    }
}
