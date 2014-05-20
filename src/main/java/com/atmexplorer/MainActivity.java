package com.atmexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.media.SoundPool;
import android.os.Bundle;
import com.atmexplorer.adapter.NavigationAdapter;
import com.atmexplorer.model.SpinnerNavigationItem;

import java.util.ArrayList;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    private ActionBar mActionBar;
    private ArrayList<SpinnerNavigationItem> mNavigationItemList;
    private NavigationAdapter mNavigationAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        mNavigationItemList = new ArrayList<SpinnerNavigationItem>();
        mNavigationItemList.add(new SpinnerNavigationItem(getResources().getString(R.string.navigation_item_title_list),
                R.drawable.ic_action_view_as_list));
        mNavigationItemList.add(new SpinnerNavigationItem(getResources().getString(R.string.navigation_item_title_map),
                R.drawable.ic_action_map));

        mNavigationAdapter = new NavigationAdapter(getApplicationContext(), mNavigationItemList);

        mActionBar.setListNavigationCallbacks(mNavigationAdapter, this);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }
}
