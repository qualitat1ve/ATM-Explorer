package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.DataManager;
import com.atmexplorer.R;
import com.atmexplorer.model.ATMItem;

import java.util.List;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Mode for detail info about item
 */
public class DetailMode extends Fragment implements Mode {

    private  DataManager mDataManager = null;

    private View mDetailView;
    private ImageView mBankLogo;
    private TextView mBankNameView;
    private TextView mAddressView;

    public DetailMode(View rootView, DataManager dataManager, final ModesManager.ModeChangeRequester modeChangeRequester) {
        mDataManager = dataManager;
        LayoutInflater layoutInflater = LayoutInflater.from(rootView.getContext());
        mDetailView =  layoutInflater.inflate(R.layout.detail_fragment, (ViewGroup) rootView, false);
        mBankNameView = (TextView)mDetailView.findViewById(R.id.bank_id);
        mBankLogo     = (ImageView)mDetailView.findViewById(R.id.logo_id);
        mAddressView  = (TextView)mDetailView.findViewById(R.id.address_desc);
        TextView mShowMap      = (TextView)mDetailView.findViewById(R.id.show_map_id);
        mShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeChangeRequester.onModeChange(ModesManager.ModeIndex.MAP);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mDetailView;
    }

    @Override
    public void onChangeState(ActiveState state) {
        switch (state) {
            case ACTIVE:
                setupInfo();
                break;
            case INACTIVE:
                break;
            default:
                break;
        }
    }

    @Override
    public Fragment getModeFragment() {
        return this;
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {
    }

    private void setupInfo() {
        ATMItem currentItem = mDataManager.getCurrentItem();
        if(currentItem!=null) {
            Log.i("detail", "setupInfo current name " + currentItem.getBankName());
            mBankNameView.setText(currentItem.getBankName());
            mBankLogo.setImageResource(currentItem.getIconId());
            mAddressView.setText(currentItem.getFullAddress());
        }
    }
}
