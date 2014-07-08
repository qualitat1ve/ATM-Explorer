package com.atmexplorer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.R;
import com.atmexplorer.database.DataBaseAdapter;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief adapter which should bind data from database with ListView
 */
public class ATMItemAdapter extends CursorAdapter {
    private Context mContext;
    private ImageView mBankLogo;
    private TextView mAddress;
    private TextView mBankName;
    private TextView mDistance;

    public ATMItemAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(R.layout.list_view_item_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String addressCity = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseAdapter.KEY_ADDRESS_STREET));
        String addressStreet = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseAdapter.KEY_ADDRESS_STREET));
        String bankName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseAdapter.KEY_ADDRESS_STREET));

        //TODO: remove hardcoded logo
        mBankLogo = (ImageView) view.findViewById(R.id.atm_logo);
        mBankLogo.setImageResource(R.drawable.credit_agricole);

        mAddress = (TextView) view.findViewById(R.id.atm_address);
        mAddress.setText(addressCity + ", " + addressStreet);

        mBankName = (TextView) view.findViewById(R.id.atm_bank_name);
        mBankName.setText(bankName);

        //TODO: add proper distance calculation
        mDistance = (TextView) view.findViewById(R.id.distance);
    }
}
