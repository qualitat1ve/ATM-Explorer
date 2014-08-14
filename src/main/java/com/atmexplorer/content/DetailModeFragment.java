package com.atmexplorer.content;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.atmexplorer.R;
import com.atmexplorer.SharedData;
import com.atmexplorer.mode.ModesManager;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.utils.Should;

import java.util.Locale;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Fragment that presents Detail Screen
 */
public class DetailModeFragment extends Fragment {

    private enum MapType {
        Google, Yandex;
    }

    private View mDetailView;
    private ImageView mBankLogo;
    private TextView mBankNameView;
    private TextView mAddressView;
    private TextView mOperMode;
    private TextView mExtraDetail;
    private SharedData mSharedData;

    public DetailModeFragment(final View rootView, final ModesManager.ModeChangeRequester requesting, SharedData data) {
        Should.beNotNull(data, "SharedData must be not null!");
        Should.beNotNull(rootView, "Root view must be not null!");
        Should.beNotNull(requesting, "ModeChangeRequester must be not null!");
        mSharedData = data;
        LayoutInflater layoutInflater = LayoutInflater.from(rootView.getContext());
        mDetailView = layoutInflater.inflate(R.layout.detail_fragment, (ViewGroup) rootView, false);
        mBankNameView = (TextView) mDetailView.findViewById(R.id.bank_id);
        mBankLogo = (ImageView) mDetailView.findViewById(R.id.logo_id);
        mAddressView = (TextView) mDetailView.findViewById(R.id.address_desc);
        mOperMode = (TextView) mDetailView.findViewById(R.id.opermode_desc);
        mExtraDetail = (TextView) mDetailView.findViewById(R.id.place_desc);

        TextView mShowMap = (TextView) mDetailView.findViewById(R.id.show_map_id);
        mShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requesting.onModeChange(ModesManager.ModeIndex.MAP, false);
            }
        });

        TextView mNavigation = (TextView) mDetailView.findViewById(R.id.navigation_id);
        mNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = null;
               String settingValue = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString(SharedData.NAVIGATION_TYPE_PREFERENCE, null);
               if (MapType.Google.name().equals(settingValue)) {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", mSharedData.getCurrentItem().getLatitude(),
                            mSharedData.getCurrentItem().getLongitude(),
                            mSharedData.getCurrentItem().getBankName() + ", " +
                                    mSharedData.getCurrentItem().getAddress());
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                } else {
                    intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
                    intent.setPackage("ru.yandex.yandexnavi");
                    intent.putExtra("lat_to", mSharedData.getCurrentItem().getLatitude());
                    intent.putExtra("lon_to", mSharedData.getCurrentItem().getLongitude());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mDetailView;
    }

    public void setupView() {
        ATMItem currentItem = mSharedData.getCurrentItem();
        if (currentItem != null) {
            Log.i("detail", "setupInfo current name " + currentItem.getBankName());
            mBankNameView.setText(currentItem.getBankName());
            mBankLogo.setImageResource(currentItem.getIconId());
            mAddressView.setText(currentItem.getFullAddress());
            mOperMode.setText(currentItem.getWorkingTime());
            mExtraDetail.setText(currentItem.getDescription());
        }
    }
}
