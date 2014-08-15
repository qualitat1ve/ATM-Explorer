package com.atmexplorer.content;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.atmexplorer.CustomATMLoader;
import com.atmexplorer.SharedData;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.adapter.ATMItemListAdapter;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.mode.ModesManager;
import com.atmexplorer.model.ATMItem;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that presents Main Screen
 */
public class MainModeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ATMItem>>{

    private DataBaseAdapter mDataBaseAdapter;
    private ATMItemListAdapter mItemAdapter;
    private Context mContext;
    private LocationTracker mLocationTracker;
    private ModesManager.ModeChangeRequester mModeChangeRequester;
    private ListView mListView;
    private SharedData mSharedData;

    public MainModeFragment(LocationTracker locationTracker, ModesManager.ModeChangeRequester requesting, SharedData data) {
        mLocationTracker = locationTracker;
        mModeChangeRequester = requesting;
        mSharedData = data;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_atm_list_fragment, null);
        mListView = (ListView) view.findViewById(R.id.atm_list);
        setHasOptionsMenu(true);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mDataBaseAdapter = new DataBaseAdapter(getActivity());
        mDataBaseAdapter.createDatabase();
        mDataBaseAdapter.open();
        mItemAdapter = new ATMItemListAdapter(mContext, mLocationTracker);
        mListView.setAdapter(mItemAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ATMItem item = (ATMItem) adapterView.getItemAtPosition(i);
                mSharedData.setCurrentItem(item);
                mModeChangeRequester.onModeChange(ModesManager.ModeIndex.DETAIL, true);
            }
        });
        getLoaderManager().initLoader(0, null, MainModeFragment.this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    getLoaderManager().restartLoader(0, null, MainModeFragment.this);
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onDestroy() {
        super.onDestroy();
        mDataBaseAdapter.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListView != null) {
            mListView.clearChoices();
            mSharedData.clearAll();
        }
    }

    public void clearSearchResults() {
        getLoaderManager().restartLoader(0, null, this);
    }

    public void filterByString(String query) {
        mItemAdapter.filterListByAddress(query);
    }

    @Override
    public Loader<List<ATMItem>> onCreateLoader(int i, Bundle bundle) {
        return new CustomATMLoader(mContext, mDataBaseAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<ATMItem>> listLoader, List<ATMItem> atmItems) {
        mItemAdapter.setData(atmItems);
    }

    @Override
    public void onLoaderReset(Loader<List<ATMItem>> listLoader) {
        mItemAdapter.cleatData();
    }
}
