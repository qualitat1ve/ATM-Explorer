package com.atmexplorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.MapFragment;

/**
 * @author Maks Kukushkin(maks.kukushkin@gmail.com)
 * @brief MapFragment wrapper which provides callback that
 * notifies subscribers that fragment view already created
 */
public class GoogleMapFragment extends MapFragment {
    public static final int ZOOM_LEVEL = 13;
    private OnMapReadyListener mOnMapReadyListener;

    public GoogleMapFragment(OnMapReadyListener listener) {
        super();
        mOnMapReadyListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(inflater, viewGroup, bundle);
        if (mOnMapReadyListener != null){
            mOnMapReadyListener.onMapReady();
        }
        return view;
    }

    public interface OnMapReadyListener {
        void onMapReady();
    }
}
