package com.atmexplorer.mode;

import android.app.SearchManager;
import android.content.Intent;
import com.atmexplorer.SharedData;
import com.atmexplorer.content.MainModeFragment;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class should display list of ATM
 */
public class MainMode extends BaseMode {

    private static final String LOG_TAG = MainMode.class.getSimpleName();

    private MainModeFragment mListFragment;

    public MainMode(SharedData sharedData, MainModeFragment contentFragment) {
        super(sharedData, contentFragment);
        mListFragment = contentFragment;
    }

    public void onBackPressed() {
        mListFragment.clearSearchResults();
    }

    public void doSearch(String query) {
        mListFragment.filterByString(query);
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    @Override
    public void onChangeState(ActiveState state) {}

    @Override
    protected void setupMode() {}

    @Override
    protected void deactivateMode() {}


}
