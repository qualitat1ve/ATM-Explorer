package com.atmexplorer.mode;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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

import java.util.Locale;


/**
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 * @brief Mode for detail info about item
 */
public class DetailMode extends BaseMode {

    private enum MapType {
        Google, Yandex;
    }

    private  DataManager mDataManager = null;
    private View mDetailView;
    private ImageView mBankLogo;
    private TextView mBankNameView;
    private TextView mAddressView;
    private TextView mOperMode;
    private TextView mExtraDetail;
    private MapType mMapType = MapType.Google;

    public DetailMode(View rootView, DataManager dataManager, final ModesManager.ModeChangeRequester modeChangeRequester) {
        super();
        mDataManager = dataManager;
        LayoutInflater layoutInflater = LayoutInflater.from(rootView.getContext());
        mDetailView =  layoutInflater.inflate(R.layout.detail_fragment, (ViewGroup) rootView, false);
        mBankNameView = (TextView)mDetailView.findViewById(R.id.bank_id);
        mBankLogo     = (ImageView)mDetailView.findViewById(R.id.logo_id);
        mAddressView  = (TextView)mDetailView.findViewById(R.id.address_desc);
        mOperMode     = (TextView)mDetailView.findViewById(R.id.opermode_desc);
        mExtraDetail  = (TextView)mDetailView.findViewById(R.id.place_desc);

        TextView mShowMap      = (TextView)mDetailView.findViewById(R.id.show_map_id);
        mShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeChangeRequester.onModeChange(ModesManager.ModeIndex.MAP);
            }
        });

        TextView mNavigation = (TextView)mDetailView.findViewById(R.id.navigation_id);
        mNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (mMapType == MapType.Google) {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", mDataManager.getCurrentItem().getLatitude(),
                            mDataManager.getCurrentItem().getLongitude(),
                            mDataManager.getCurrentItem().getBankName() + ", " +
                                    mDataManager.getCurrentItem().getAddress());
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                } else {
                    intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
                    intent.setPackage("ru.yandex.yandexnavi");
                    intent.putExtra("lat_to", mDataManager.getCurrentItem().getLatitude());
                    intent.putExtra("lon_to", mDataManager.getCurrentItem().getLongitude());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mDetailView;
    }

    @Override
    public Fragment getModeFragment() {
        return this;
    }

    @Override
    protected void setupMode() {
        ATMItem currentItem = mDataManager.getCurrentItem();
        if(currentItem!=null) {
            Log.i("detail", "setupInfo current name " + currentItem.getBankName());
            mBankNameView.setText(currentItem.getBankName());
            mBankLogo.setImageResource(currentItem.getIconId());
            mAddressView.setText(currentItem.getFullAddress());
            mOperMode.setText(currentItem.getWorkingTime());
            mExtraDetail.setText(currentItem.getDescription());
        }
    }

    @Override
    protected void deactivateMode() {

    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onBackPressed() {
    }
}
