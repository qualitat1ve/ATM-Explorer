package com.atmexplorer;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.atmexplorer.adapter.ATMItemListAdapter;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.GeoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class should display list of ATM
 */
public class ATMListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<ATMItem>> {

    private static final String LOG_TAG = ATMListFragment.class.getSimpleName();
    private DataBaseAdapter mDataBaseAdapter;
    private ATMItemListAdapter mItemAdapter;
    private Context mContext;
    private List<ATMItem> mSelectedItems = new ArrayList<ATMItem>();

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
        mItemAdapter = new ATMItemListAdapter(mContext, ((MainActivity) getActivity()).getLocationTracker());
        setListAdapter(mItemAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ATMItem item = (ATMItem) getListView().getItemAtPosition(position);
        if (!mSelectedItems.contains(item)) {
            mSelectedItems.add(item);
        } else {
            mSelectedItems.remove(item);
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
            getSelectedItems().clear();
        }
    }

    public final List<ATMItem> getSelectedItems() {
        return mSelectedItems;
    }

}
