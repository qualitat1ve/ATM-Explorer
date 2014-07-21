package com.atmexplorer;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.atmexplorer.database.DataBaseAdapter;
import com.atmexplorer.database.DataBaseObserver;
import com.atmexplorer.model.ATMItem;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief simple data loader to improve performance during working with DB
 */
public class CustomATMLoader extends AsyncTaskLoader<List<ATMItem>> {

    private DataBaseAdapter mDataBaseAdapter;
    private List<ATMItem> mItems;
    private DataBaseObserver mObserver;

    public CustomATMLoader(Context context, DataBaseAdapter dataBase) {
        super(context);
        mDataBaseAdapter = dataBase;
    }

    public List<ATMItem> loadInBackground() {
        return mDataBaseAdapter.getAllData();
    }

    public void deliverResult(List<ATMItem> itemsList) {
        if (isReset()) {
            return;
        }
        List<ATMItem> oldData = mItems;
        mItems = itemsList;
        if (isStarted()) {
            super.deliverResult(mItems);
        }

        if (oldData != null && oldData != itemsList) {
            releaseData(oldData);
        }
    }

    protected void onStartLoading() {
        if (mItems != null) {
            deliverResult(mItems);
        }

        if(mObserver == null) {
            mObserver = new DataBaseObserver();
            //TODO: register the observer
        }

        if (takeContentChanged() || mItems == null) {
            forceLoad();
        }
    }

    protected void onStopLoading() {
        cancelLoad();
    }

    protected void onReset() {
        onStopLoading();
        if (mItems != null) {
            releaseData(mItems);
        }
        if (mObserver != null) {
            releaseData(mObserver);
        }
    }

    public void onCanceled(List<ATMItem> itemsList){
        super.onCanceled(itemsList);
        releaseData(itemsList);
    }

    private void releaseData(Object data) {
        data = null;
    }
}
