package com.atmexplorer.mode;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.atmexplorer.CustomATMLoader;
import com.atmexplorer.DataManager;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.adapter.ATMItemListAdapter;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.GeoUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class should display list of ATM
 */
public class ListMode extends ListFragment implements LoaderManager.LoaderCallbacks<List<ATMItem>>, Mode {

    private static final String LOG_TAG = ListMode.class.getSimpleName();
    private DataBaseAdapter mDataBaseAdapter;
    private ATMItemListAdapter mItemAdapter;
    private Context mContext;
    private LocationTracker mLocationTracker;
    private DataManager mDataManager;
    private ModesManager.ModeChangeRequester mModeChangeRequester;

    public ListMode(DataManager dataManager, LocationTracker locationTracker, ModesManager.ModeChangeRequester modeChangeRequester) {
        mLocationTracker = locationTracker;
        mDataManager = dataManager;
        mModeChangeRequester = modeChangeRequester;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.atm_list_fragment_layout, null);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mDataBaseAdapter = new DataBaseAdapter(getActivity());
        mDataBaseAdapter.createDatabase();
        mDataBaseAdapter.open();
        mItemAdapter = new ATMItemListAdapter(mContext, mLocationTracker, new DetailClickListener());
        setListAdapter(mItemAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ATMItem item = (ATMItem) getListView().getItemAtPosition(position);
        if(!mDataManager.contains(item)) {
            mDataManager.addItem(item);
        }else{
            mDataManager.removeItem(item);
        }
    }

    @Override
    public Loader<List<ATMItem>> onCreateLoader(int i, Bundle bundle) {
        return new CustomATMLoader(mContext, mDataBaseAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<ATMItem>> listLoader, List<ATMItem> atmItems) {
        mItemAdapter.setData(atmItems, false);
    }

    private void fillCoordinates(List<ATMItem> atmItems) {
        for (ATMItem item : atmItems) {
            try {
                Pair<Double, Double> value = GeoUtils.getInstance(getActivity().getBaseContext()).getCoordinates(item.getFullAddress());
                item.setLatitude(value.first);
                item.setLongitude(value.second);
                Log.i(LOG_TAG, "ID = " + item.getId() + "; lat " + value.first + " long " + value.second);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Something went wrong with coordinates ", e);
            }
        }
    }

    public void doSearch(String query) {
        mItemAdapter.setData(mDataBaseAdapter.getFilteredData(query), true);
    }

    @Override
    public void onLoaderReset(Loader<List<ATMItem>> listLoader) {
        mItemAdapter.cleatData();
    }

    public void onBackPressed() {
        mItemAdapter.releaseSearchResult();
    }

    public void onDestroy() {
        super.onDestroy();
        mDataBaseAdapter.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getListView()!=null) {
            getListView().clearChoices();
            mDataManager.clearAll();
        }
    }

    @Override
    public void onChangeState(ActiveState state) {
    }

    @Override
    public Fragment getModeFragment() {
        return this;
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

    public class DetailClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mModeChangeRequester.onModeChange(ModesManager.ModeIndex.DETAIL);
        }
    }
}
