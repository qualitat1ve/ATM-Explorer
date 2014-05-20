package com.atmexplorer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.R;
import com.atmexplorer.model.SpinnerNavigationItem;

import java.util.ArrayList;

/**
 * Created by m.kukushkin on 20.05.2014.
 */
public class NavigationAdapter extends BaseAdapter {
    private ImageView mItemIcon;
    private TextView mItemTitle;
    private ArrayList<SpinnerNavigationItem> mItemsList;
    private Context mContext;

    public NavigationAdapter(Context context, ArrayList<SpinnerNavigationItem> itemsList) {
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.navigation_item_list, null);
        }

        mItemIcon = (ImageView) convertView.findViewById(R.id.navigationItemIcon);
        mItemIcon.setImageResource(mItemsList.get(position).getIconId());

        mItemTitle = (TextView) convertView.findViewById(R.id.navigationItemTitle);
        mItemTitle.setText(mItemsList.get(position).getItemTitle());

        return convertView;
    }
}
