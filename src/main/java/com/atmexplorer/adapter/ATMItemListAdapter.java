package com.atmexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.R;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.Should;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief adapter which should bind data from database with ListView
 */
public class ATMItemListAdapter extends BaseAdapter {
    private static final String LOG_TAG = ATMItemListAdapter.class.getSimpleName();

    private Context mContext;
    private List<ATMItem> mATMList = new ArrayList<ATMItem>();
    private TextView mDistance;
    private ViewHolder mViewHolder;

    public ATMItemListAdapter(Context context) {
        mContext = context;
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
        Should.beNotNull(list, LOG_TAG + "; ATM list should be not null!");
        mATMList = list;
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
            convertedView = inflater.inflate(R.layout.list_view_item_layout, null);
            mViewHolder = new ViewHolder();
            mViewHolder.addressHolder = (TextView) convertedView.findViewById(R.id.atm_address);
            mViewHolder.bankLogoHolder = (ImageView) convertedView.findViewById(R.id.atm_logo);
            mViewHolder.bankNameHolder = (TextView) convertedView.findViewById(R.id.atm_bank_name);
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

        return convertedView;
    }

    private static class ViewHolder {
        ImageView bankLogoHolder;
        TextView bankNameHolder;
        TextView addressHolder;
    }
}
