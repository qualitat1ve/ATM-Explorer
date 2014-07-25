package com.atmexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import com.atmexplorer.adapter.NavigationAdapter;
import com.atmexplorer.model.ATMItem;
import com.atmexplorer.model.SpinnerNavigationItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 */

public class MainActivity extends Activity implements ActionBar.OnNavigationListener,
        ATMListFragment.OnItemSelectedListener, GoogleMapFragment.OnMapReadyListener {

    private ActionBar mActionBar;
    private List<SpinnerNavigationItem> mNavigationItemList;
    private NavigationAdapter mNavigationAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private ATMListFragment mATMListFragment;
    private MapFragment mMapFragment;
    private View mDrawerMenu;
    private int mActionBarIconId;
    private LocationTracker mLocationTracker;
    private ATMItem mSeclectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMapFragment = new GoogleMapFragment(this);
        mMapFragment.setRetainInstance(true);
        mLocationTracker = new LocationTracker(getApplicationContext());
        mATMListFragment = new ATMListFragment();
        Button mapButton = (Button)findViewById(R.id.show_on_map);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mATMListFragment);
            fragmentTransaction.commit();
        }

        setUpActionBar();
        setUpDrawer();

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mMapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void setUpGoogleMap() {
        GoogleMap mGoogleMap = mMapFragment.getMap();
        if (mGoogleMap == null) {
            return;
        }
        double latitude = mLocationTracker.getLatitude();
        double longitude = mLocationTracker.getLongitude();


        if(mSeclectedItem!=null) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(mSeclectedItem.getLatitude(), mSeclectedItem.getLongitude())).
                    title(mSeclectedItem.getBankName() + ", " + mSeclectedItem.getAddress());
            mGoogleMap.addMarker(marker);
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        CameraPosition position = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).
                zoom(GoogleMapFragment.ZOOM_LEVEL).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private void setUpDrawer() {
        mDrawerMenu = findViewById(R.id.left_drawer);
        mDrawerTitle = getResources().getString(R.string.drawer_open_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open_title, R.string.drawer_close_title) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mActionBar.setIcon(getResources().getDrawable(R.drawable.ic_logo));
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                mActionBar.setDisplayShowTitleEnabled(true);
                if (mActionBarIconId == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_drawer_message), Toast.LENGTH_SHORT).show();
                } else {
                    mActionBar.setIcon(getResources().getDrawable(mActionBarIconId));
                    mActionBar.setTitle(mDrawerTitle);
                }
                mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setUpActionBar() {
        mActionBar = getActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        mNavigationItemList = new ArrayList<SpinnerNavigationItem>();

        mNavigationItemList.add(new SpinnerNavigationItem(getResources().getString(R.string.navigation_item_title_list),
                R.drawable.ic_action_view_as_list, mATMListFragment));
        mNavigationItemList.add(new SpinnerNavigationItem(getResources().getString(R.string.navigation_item_title_map),
                R.drawable.ic_action_map, mMapFragment));

        mNavigationAdapter = new NavigationAdapter(getApplicationContext(), mNavigationItemList);

        mActionBar.setListNavigationCallbacks(mNavigationAdapter, this);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    public LocationTracker getLocationTracker() {
        return mLocationTracker;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mNavigationAdapter.getItemsFragment(itemPosition));
        fragmentTransaction.commit();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerMenu);
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemSelected(final ATMItem item) {
        mActionBarIconId = item.getIconId();
        mDrawerTitle = item.getBankName();
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mSeclectedItem = item;
    }

    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerMenu)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady() {
        setUpGoogleMap();
    }
}
