package com.atmexplorer.mode;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.builder.DetailBuilder;
import com.atmexplorer.builder.ListModeBuilder;
import com.atmexplorer.builder.MapModeBuilder;
import com.atmexplorer.utils.Should;

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

    public ModesManager(Activity activity) {
        Should.beNotNull(activity, "Main activity is null!");
        mActivity = activity;
        mActionBar = mActivity.getActionBar();
        DataManager dataManager = new DataManager();

        LocationTracker locationTracker =  new LocationTracker(activity.getApplicationContext());
        ModeChangeRequester modeChangeRequester = new ModeChangeRequester();

        View rootView  = activity.findViewById(R.id.fragment_container);

        ListModeBuilder listModeBuilder =  new ListModeBuilder(rootView, dataManager, locationTracker, modeChangeRequester);
        mModes[ModeIndex.LIST.index()] = listModeBuilder.build();

        MapModeBuilder mapModeBuilder =  new MapModeBuilder(rootView, dataManager, activity.getApplicationContext(), locationTracker, modeChangeRequester);
        mModes[ModeIndex.MAP.index()] = mapModeBuilder.build();
        mActiveMode = mModes[mDefaultModeIndex];

        DetailBuilder detailBuilder =  new DetailBuilder(rootView, dataManager, modeChangeRequester);
        mModes[ModeIndex.DETAIL.index()] = detailBuilder.build();

        activateDefaultMode();
    }

    public void activate(ModeIndex modeId) {
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
        mActiveMode.onChangeState(Mode.ActiveState.INACTIVE);
        mActiveMode = mModes[modeId.index()];
        mActiveMode.onChangeState(Mode.ActiveState.ACTIVE);
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, mModes[modeId.index()].getModeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void activateDefaultMode() {
        FragmentTransaction fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
        if(fragmentTransaction.isEmpty()) {
            fragmentTransaction.add(R.id.fragment_container, (Fragment)mModes[mDefaultModeIndex]);
        }else{
            //TODO
        }
        fragmentTransaction.commit();
    }

    public Mode getActiveMode() {
        return  mActiveMode;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onNewIntent(Intent intent) {
        mActiveMode.onNewIntent(intent);
    }

    public void onBackPressed() {
        mActiveMode.onBackPressed();
    }

    public final class ModeChangeRequester {
        public void onModeChange(ModeIndex index) {
            activate(index);
        }
    }
}
