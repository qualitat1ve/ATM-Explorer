package com.atmexplorer;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
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
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class should display list of ATM
 */
public class ATMListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<ATMItem>> {

    private static final String LOG_TAG = ATMListFragment.class.getSimpleName();
    private OnItemSelectedListener mOnItemSelectedListener;
    private DataBaseAdapter mDataBaseAdapter;
    private ATMItemListAdapter mItemAdapter;
    private EditText mFilter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.atm_list_fragment_layout, null);
        mFilter = (EditText) view.findViewById(R.id.text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //unused callback
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //TODO: implement filter inside adapter
//                mItemAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //unused callback
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataBaseAdapter = new DataBaseAdapter(getActivity());
        mDataBaseAdapter.createDatabase();
        mDataBaseAdapter.open();
        mItemAdapter = new ATMItemListAdapter(getActivity().getApplicationContext());
        setListAdapter(mItemAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mOnItemSelectedListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, activity.toString() + "must implement OnItemSelectedListener");
            throw e;
        }
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ATMItem item = (ATMItem) listView.getItemAtPosition(position);
        mOnItemSelectedListener.onItemSelected(item.getIconId(), item.getBankName());
    }

    @Override
    public Loader<List<ATMItem>> onCreateLoader(int i, Bundle bundle) {
        return new CustomATMLoader(getActivity().getApplicationContext(), mDataBaseAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<ATMItem>> listLoader, List<ATMItem> atmItems) {
        mItemAdapter.setData(atmItems);
    }

    private void fillCoordinates(List<ATMItem> atmItems) {
        for (ATMItem item : atmItems) {
            try {
                Pair<Double, Double> value = GeoUtils.getInstance(getActivity().getBaseContext()).getCoordinates(item.getFullAddress());
                item.setLatitude(value.first);
                item.setLongitude(value.second);
                Log.i("geo", "ID = " + item.getId() + "; lat " + value.first + " long " + value.second);
            } catch (IOException e) {
                Log.e("geo", "error " + e);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ATMItem>> listLoader) {
        mItemAdapter.cleatData();
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(int iconId, String bankName);
    }

    public void onDestroy() {
        super.onDestroy();
        mDataBaseAdapter.close();
    }
}
