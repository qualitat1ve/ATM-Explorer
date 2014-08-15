package com.atmexplorer.adapter;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.LocationTracker;
import com.atmexplorer.R;
import com.atmexplorer.SharedData;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.Should;
import com.atmexplorer.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief adapter which should bind data from database with ListView
 */
public class ATMItemListAdapter extends BaseAdapter {
    private static final String LOG_TAG = ATMItemListAdapter.class.getSimpleName();

    private Context mContext;
    private List<ATMItem> mATMList = new ArrayList<ATMItem>();
    private ViewHolder mViewHolder;
    private Location mCurrentLocation;


    public ATMItemListAdapter(Context context, LocationTracker locationTracker) {
        mContext = context;
        mCurrentLocation = locationTracker.getLocation();
    }

    @Override
    public int getCount() {
        return mATMList.size();
    }

    @Override
    public Object getItem(int i) {
        return mATMList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setData(List<ATMItem> list) {
        int settingValue = PreferenceManager.getDefaultSharedPreferences(mContext).getInt(SharedData.LIMIT_DISTANCE, 0);
        Should.beNotNull(list, LOG_TAG + "; ATM list should be not null!");
        LinkedList<Pair<ATMItem, Float>> sortedList = new LinkedList<Pair<ATMItem, Float>>();
        for (ATMItem item : list) {
            float distance = mCurrentLocation.distanceTo(item.getLocation());
            if (distance > settingValue) continue;
            sortedList.add(new Pair<ATMItem, Float>(item, distance));
        }
        Collections.sort(sortedList, new DistanceComparator());
        mATMList = new ArrayList<ATMItem>(sortedList.size());
        for (Pair<ATMItem, Float> pair : sortedList) {
            mATMList.add(pair.first);
        }
        notifyDataSetChanged();
    }

    public void filterListByAddress(String filterByAddress) {
        ArrayList<ATMItem> filteredList = new ArrayList<ATMItem>();
        for (ATMItem item : mATMList) {
            if (item.getAddress().toLowerCase().contains(filterByAddress.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mATMList.clear();
        mATMList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void cleatData() {
        mATMList.clear();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertedView = view;
        if (convertedView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertedView = inflater.inflate(R.layout.layout_list_view_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.addressHolder = (TextView) convertedView.findViewById(R.id.atm_address);
            mViewHolder.bankLogoHolder = (ImageView) convertedView.findViewById(R.id.atm_logo);
            mViewHolder.bankNameHolder = (TextView) convertedView.findViewById(R.id.atm_bank_name);
            mViewHolder.distanceView = (TextView) convertedView.findViewById(R.id.distance);
            convertedView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertedView.getTag();
        }
        Should.beNotNull(convertedView, LOG_TAG + "; view should be not null!");
        ATMItem item = mATMList.get(i);
        Should.beNotNull(item, LOG_TAG + ", ATMList should not contain null!");

        mViewHolder.bankLogoHolder.setImageResource(item.getIconId());
        mViewHolder.bankNameHolder.setText(item.getBankName());
        mViewHolder.addressHolder.setText(item.getFullAddress());
        float distance = mCurrentLocation.distanceTo(item.getLocation());
        String dist;
        if (distance < 1000) {
            int newDistance = (int)distance;
            dist = newDistance + " "  + mContext.getResources().getString(R.string.distance_meter);
        } else {
            float newDistance = Utils.round(distance / 1000, 1);
            dist = newDistance + " "  + mContext.getResources().getString(R.string.distance_kilometer);
        }
        mViewHolder.distanceView.setText(dist);

        return convertedView;
    }

    public static class ViewHolder {
        ImageView bankLogoHolder;
        TextView bankNameHolder;
        TextView addressHolder;
        TextView distanceView;
    }

    private class DistanceComparator implements Comparator<Pair<ATMItem, Float> > {
        @Override
        public int compare(Pair<ATMItem, Float> atmItemFloatPair, Pair<ATMItem, Float> atmItemFloatPair2) {
            return Float.compare(atmItemFloatPair.second, atmItemFloatPair2.second);
        }
    }
}
