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
import com.atmexplorer.model.ATMItem;

import java.util.ArrayList;

/**
 * Created by m.kukushkin on 03.06.2014.
 */
public class ATMItemAdapter extends BaseAdapter {
    private ArrayList<ATMItem> mItemsList;
    private Context mContext;
    private ImageView mBankLogo;
    private TextView mAddress;
    private TextView mBankName;
    private TextView mDistance;

    public ATMItemAdapter(Context context, ArrayList<ATMItem> itemsList) {
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
            convertView = inflater.inflate(R.layout.list_view_item_layout, null);
        }
        mBankLogo = (ImageView) convertView.findViewById(R.id.atm_logo);
        mBankLogo.setImageResource(mItemsList.get(position).getIconId());

        mAddress = (TextView) convertView.findViewById(R.id.atm_address);
        mAddress.setText(mItemsList.get(position).getAddress());

        mBankName = (TextView) convertView.findViewById(R.id.atm_bank_name);
        mBankName.setText(mItemsList.get(position).getBankName());

        mDistance = (TextView) convertView.findViewById(R.id.distance);

        return convertView;
    }
}
