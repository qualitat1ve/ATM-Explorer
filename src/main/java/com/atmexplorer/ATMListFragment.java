package com.atmexplorer;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import com.atmexplorer.adapter.ATMItemAdapter;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.database.FilterCursorWrapper;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief class should display list of ATM
 */
public class ATMListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = ATMListFragment.class.getSimpleName();
    private OnItemSelectedListener mCallback;
    private DataBaseAdapter mDataBase;
    private ATMItemAdapter mItemAdapter;
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
                mItemAdapter.getFilter().filter(charSequence);
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

        mDataBase = new DataBaseAdapter(getActivity());
        mDataBase.createDatabase();
        mDataBase.open();

        mItemAdapter = new ATMItemAdapter(getActivity().getApplicationContext(), null);
        mItemAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                Cursor cursor = mDataBase.getAllData();
                return new FilterCursorWrapper(cursor, charSequence.toString(),
                        cursor.getColumnIndex(DataBaseAdapter.KEY_ADDRESS_STREET),
                        cursor.getColumnIndex(DataBaseAdapter.KEY_ADDRESS_STREET));
            }
        });

        setListAdapter(mItemAdapter);

        getLoaderManager().initLoader(0, null, this);

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, activity.toString() + "must implement OnItemSelectedListener");
            throw e;
        }
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        //TODO: remove hardcoded image resource
        Cursor cursor = ((CursorAdapter)listView.getAdapter()).getCursor();
        mCallback.onItemSelected(R.drawable.credit_agricole, cursor.getString(cursor.getColumnIndex(DataBaseAdapter.KEY_BANK_NAME)));
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new CustomCursorLoader(getActivity().getApplicationContext(), mDataBase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mItemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //unused callback
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(int iconId, String bankName);
    }

    public void onDestroy() {
        super.onDestroy();
        mDataBase.close();
    }

    static class CustomCursorLoader extends CursorLoader {
        private DataBaseAdapter mDataBaseAdapter;

        public CustomCursorLoader(Context context, DataBaseAdapter dataBase) {
            super(context);
            mDataBaseAdapter = dataBase;
        }

        public Cursor loadInBackground() {
            return mDataBaseAdapter.getAllData();
        }
    }
}
