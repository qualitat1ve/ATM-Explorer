package com.atmexplorer.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.R;
import com.atmexplorer.model.SpinnerNavigationItem;

import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief adapter which should bind data from ArrayList with ListView
 */
public class NavigationAdapter extends BaseAdapter {
    private ImageView mItemIcon;
    private TextView mItemTitle;
    private List<SpinnerNavigationItem> mItemsList;
    private Context mContext;

    public NavigationAdapter(Context context, List<SpinnerNavigationItem> itemsList) {
        mContext = context;
        mItemsList = itemsList;
    }

    @Override
    public int getCount() {
        return mItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.navigation_list_item, null);
        }

        mItemIcon = (ImageView) view.findViewById(R.id.navigationItemIcon);
        mItemIcon.setImageResource(mItemsList.get(position).getIconId());

        mItemTitle = (TextView) view.findViewById(R.id.navigationItemTitle);
        mItemTitle.setText(mItemsList.get(position).getItemTitle());

        return view;
    }

    public Fragment getItemsFragment(int position) {
        return mItemsList.get(position).getFragment();
    }
}
